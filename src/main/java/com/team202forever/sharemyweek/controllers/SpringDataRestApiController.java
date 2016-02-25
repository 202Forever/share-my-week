package com.team202forever.sharemyweek.controllers;

import com.google.common.collect.Sets;
import com.team202forever.sharemyweek.data.models.*;
import com.team202forever.sharemyweek.data.repository.UserRepository;
import com.team202forever.sharemyweek.data.repository.WeekRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.core.RepositoryConstraintViolationException;
import org.springframework.data.rest.webmvc.PersistentEntityResource;
import org.springframework.data.rest.webmvc.PersistentEntityResourceAssembler;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.SmartValidator;
import org.springframework.validation.ValidationUtils;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.BadRequestException;
import javax.ws.rs.ForbiddenException;

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
        if (bindingResult.hasErrors()) {
            throw new RepositoryConstraintViolationException(bindingResult);
        }
        HashId hashId = new HashId(id);
        Week stored = weekRepository.findOne(hashId);
        if (stored == null) {
            throw new ResourceNotFoundException();
        }
        week.setHashId(hashId);
        if (!stored.getUsers().stream().anyMatch(weekUser -> weekUser.getUserInfo().getHashId().toString().equals(userId))) {
            throw new ForbiddenException("The user is forbidden to update the week");
        }
        Sets.SetView<WeekUser> difference = Sets.difference(week.getUsers(), stored.getUsers());
        if (!difference.isEmpty()) {
            if (difference.size() > 1 || !difference.iterator().next().getUserInfo().getHashId().equals(hashId)) {
                throw new ForbiddenException("It is forbidden to update other users");
            }
        }
        return resourceAssembler.toResource(weekRepository.save(week));
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
