package com.team202forever.sharemyweek.controllers;

import com.team202forever.sharemyweek.data.models.User;
import com.team202forever.sharemyweek.data.models.ViewModel;
import com.team202forever.sharemyweek.data.models.Week;
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

@RepositoryRestController
public class SpringDataRestApiController {

    @Autowired
    private SmartValidator validator;

    @Autowired
    private WeekRepository weekRepository;

    @Autowired
    private UserRepository userRepository;

    @ResponseBody
    @RequestMapping(value = "/weeks/{id}", method = RequestMethod.PUT)
    public PersistentEntityResource saveWeek(@PathVariable("id") String id, @RequestBody Week week, BindingResult bindingResult, PersistentEntityResourceAssembler resourceAssembler) {
        ValidationUtils.invokeValidator(validator, week, bindingResult);
        if (bindingResult.hasErrors()) {
            throw new RepositoryConstraintViolationException(bindingResult);
        }
        ViewModel.HashId hashId = new ViewModel.HashId(id);
        if (weekRepository.findOne(hashId) == null) {
            throw new ResourceNotFoundException();
        }
        week.setHashId(hashId);
        return resourceAssembler.toResource(weekRepository.save(week));
    }

    @ResponseBody
    @RequestMapping(value = "/users/{id}", method = RequestMethod.PUT)
    public PersistentEntityResource saveUser(@PathVariable("id") String id, @RequestBody User user, BindingResult bindingResult, PersistentEntityResourceAssembler resourceAssembler) {
        ValidationUtils.invokeValidator(validator, user, bindingResult);
        if (bindingResult.hasErrors()) {
            throw new RepositoryConstraintViolationException(bindingResult);
        }
        ViewModel.HashId hashId = new ViewModel.HashId(id);
        if (userRepository.findOne(hashId) == null) {
            throw new ResourceNotFoundException();
        }
        user.setHashId(hashId);
        return resourceAssembler.toResource(userRepository.save(user));
    }

}
