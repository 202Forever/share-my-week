package com.team202forever.sharemyweek.controllers;

import com.team202forever.sharemyweek.data.models.*;
import com.team202forever.sharemyweek.data.repository.UserRepository;
import com.team202forever.sharemyweek.data.repository.WeekRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.core.RepositoryConstraintViolationException;
import org.springframework.data.rest.webmvc.PersistentEntityResource;
import org.springframework.data.rest.webmvc.PersistentEntityResourceAssembler;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.SmartValidator;
import org.springframework.validation.ValidationUtils;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.BadRequestException;
import javax.ws.rs.ForbiddenException;
import java.util.HashMap;
import java.util.Map;

@RepositoryRestController
public class SpringDataRestApiController {

    @Autowired
    private SmartValidator validator;

    @Autowired
    private WeekRepository weekRepository;

    @Autowired
    private UserRepository userRepository;

    @ResponseBody
    @RequestMapping(value = {"/weeks/{id}", "/weeks/{id}?userId={userId}"}, method = RequestMethod.PUT)
    public PersistentEntityResource saveWeek(@PathVariable("id") String id, @RequestParam(value = "userId", required = true) String userId, @RequestBody Week week, BindingResult bindingResult, PersistentEntityResourceAssembler resourceAssembler) {
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
        Map<String, WeekUser> userMap = new HashMap<>();
        WeekUser user = null;
        for (WeekUser weekUser : stored.getUsers()) {
            userMap.put(weekUser.getUserInfo().getEmail(), weekUser);
            if (weekUser.getUserInfo().getHashId().toString().equals(userId)) {
                user = weekUser;
            }
        }
        if (user == null) {
            throw new ForbiddenException("The user is forbidden to update the week");
        }
        if (userMap.size() > week.getUsers().size() && user.getRole() != WeekUserRole.OWNER) {
            throw new ForbiddenException("Removing users is not allowed for non-owners");
        }
        for (WeekUser weekUser : week.getUsers()) {
            WeekUser storedUser = userMap.get(weekUser.getUserInfo().getEmail());
            if (storedUser == null || !storedUser.equalsUser(weekUser) && !storedUser.getUserInfo().getHashId().toString().equals(userId)) {
                throw new ForbiddenException("It is forbidden to update other users");
            }
            weekUser.setUserInfo(storedUser.getUserInfo());
        }
        return resourceAssembler.toResource(weekRepository.save(week));
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
