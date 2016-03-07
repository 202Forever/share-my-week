package com.team202forever.sharemyweek.controllers;

import com.team202forever.sharemyweek.data.models.*;
import com.team202forever.sharemyweek.data.repository.EventRepository;
import com.team202forever.sharemyweek.data.repository.UserRepository;
import com.team202forever.sharemyweek.data.repository.WeekRepository;
import com.team202forever.sharemyweek.manager.EmailNotificationManager;
import org.hibernate.validator.constraints.Email;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.core.RepositoryConstraintViolationException;
import org.springframework.data.rest.webmvc.PersistentEntityResource;
import org.springframework.data.rest.webmvc.PersistentEntityResourceAssembler;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.hateoas.ExposesResourceFor;
import org.springframework.hateoas.Resources;
import org.springframework.http.HttpStatus;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.validation.BindingResult;
import org.springframework.validation.SmartValidator;
import org.springframework.validation.ValidationUtils;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.ForbiddenException;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@RepositoryRestController
@ExposesResourceFor(Event.class)
public class SpringDataRestApiController {

    @Autowired
    private SmartValidator validator;

    @Autowired
    private WeekRepository weekRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @Autowired
    private EmailNotificationManager emailNotificationManager;

    @ResponseBody
    @RequestMapping(value = {"/weeks/{id}", "/weeks/{id}?userId={userId}"}, method = RequestMethod.PUT)
    public PersistentEntityResource saveWeek(@PathVariable("id") String id, @RequestParam(value = "userId", required = true) String userId, @RequestBody Week week, BindingResult bindingResult, PersistentEntityResourceAssembler resourceAssembler) throws NoSuchMethodException, MessagingException {
        emailNotificationManager.testEmailServer(week);
        if (userId == null) {
            throw new BadRequestException("A user id must be specified to update the week");
        }
        ValidationUtils.invokeValidator(validator, week, bindingResult);
        for (WeekUser weekUser : week.getUsers()) {
            ValidationUtils.invokeValidator(validator, weekUser.getUserInfo(), bindingResult);
        }
        if (bindingResult.hasErrors()) {
            throw new RepositoryConstraintViolationException(bindingResult);
        }
        HashId hashId = new HashId(id);
        Week stored = weekRepository.findOne(hashId);
        if (stored == null) {
            throw new ResourceNotFoundException();
        }
        week.setHashId(hashId);
        Map<String, WeekUser> storedUserMap = new HashMap<>();
        WeekUser user = null;
        for (WeekUser weekUser : stored.getUsers()) {
            storedUserMap.put(weekUser.getUserInfo().getEmail(), weekUser);
            if (weekUser.getUserInfo().getHashId().toString().equals(userId)) {
                user = weekUser;
            }
        }
        if (user == null) {
            throw new ForbiddenException("The user is forbidden to update the week");
        }
        if (storedUserMap.size() > week.getUsers().size() && user.getRole() != WeekUserRole.OWNER) {
            throw new ForbiddenException("Removing users is not allowed for non-owners");
        }
        Map<String, WeekUser> newUsers = new HashMap<>();
        Map<String, WeekUser> exisitingUsers = new HashMap<>();
        for (WeekUser weekUser : week.getUsers()) {
            User userInfo = weekUser.getUserInfo();
            WeekUser storedUser = storedUserMap.get(userInfo.getEmail());
            User existingUserInfo = userRepository.findByEmail(userInfo.getEmail());
            if (existingUserInfo == null || storedUser == null) {
                User newUser = null;
                if (existingUserInfo == null) {
                    newUser = new User();
                    newUser.setEmail(userInfo.getEmail());
                    weekUser.setUserInfo(newUser);
                } else {
                    newUser = existingUserInfo;
                    weekUser.setUserInfo(existingUserInfo);
                }
                newUsers.put(newUser.getEmail(), weekUser);
            } else if (!storedUser.equalsUser(weekUser) && !storedUser.getUserInfo().getHashId().toString().equals(userId)) {
                throw new ForbiddenException("It is forbidden to update other users");
            } else {
                weekUser.setUserInfo(existingUserInfo);
                exisitingUsers.put(existingUserInfo.getEmail(), weekUser);
            }
        }
        if (exisitingUsers.size() != storedUserMap.size() && user.getRole() != WeekUserRole.OWNER) {
            throw new ForbiddenException("Removing users is not allowed for non-owners");
        }
        for (Map.Entry<String, WeekUser> entry : newUsers.entrySet()) {
            User existingUserInfo = userRepository.findByEmail(entry.getValue().getUserInfo().getEmail());
            if (existingUserInfo == null) {
                entry.getValue().setUserInfo(userRepository.insert(entry.getValue().getUserInfo()));
            }
        }
        week = weekRepository.save(week);
        emailNotificationManager.sendInvite(week);
        simpMessagingTemplate.convertAndSend("/topic/refreshWeeks", "");
        return resourceAssembler.toResource(week);
    }

    @RequestMapping(value = {"/weeks/{id}", "/weeks/{id}?userId={userId}"}, method = RequestMethod.DELETE)
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void deleteWeek(@PathVariable(value = "id") String id, @RequestParam(value = "userId", required = true) String userId) {
        if (userId == null) {
            throw new BadRequestException("A user id must be specified to update the event");
        }
        Week stored = weekRepository.findOne(new HashId(id));
        if (stored == null) {
            throw new ResourceNotFoundException();
        }
        User user = null;
        for (WeekUser weekUser : stored.getUsers()) {
            if (weekUser.getUserInfo().getHashId().toString().equals(userId)) {
                user = weekUser.getUserInfo();
                break;
            }
        }
        if (user == null) {
            throw new ForbiddenException("The user is forbidden to update the event");
        }
        weekRepository.delete(stored.getHashId());
    }

    @ResponseBody
    @RequestMapping(value = "/weeks/{id}/events", method = RequestMethod.GET)
    public Resources<PersistentEntityResource> getEvents(@PathVariable("id") String id, PersistentEntityResourceAssembler resourceAssembler) {
        return new Resources<>(eventRepository.findByWeekIdOrderByDateTimeRangeStartAscPriorityDesc(new HashId(id).toObjectId()).stream().map(resourceAssembler::toResource).collect(Collectors.toList()));
    }

    @ResponseBody
    @RequestMapping(value = "/users/{id}", method = RequestMethod.PUT)
    public PersistentEntityResource saveUser(@PathVariable("id") String id, @RequestBody User user, BindingResult bindingResult, PersistentEntityResourceAssembler resourceAssembler) {
        ValidationUtils.invokeValidator(validator, user, bindingResult);
        if (bindingResult.hasErrors()) {
            throw new RepositoryConstraintViolationException(bindingResult);
        }
        HashId hashId = new HashId(id);
        if (userRepository.findOne(hashId) == null) {
            throw new ResourceNotFoundException();
        }
        user.setHashId(hashId);
        return resourceAssembler.toResource(userRepository.save(user));
    }

}
