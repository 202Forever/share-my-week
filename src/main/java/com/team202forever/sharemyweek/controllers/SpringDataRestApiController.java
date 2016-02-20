package com.team202forever.sharemyweek.controllers;

import com.team202forever.sharemyweek.data.models.ViewModel;
import com.team202forever.sharemyweek.data.models.Week;
import com.team202forever.sharemyweek.data.repository.WeekRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.core.RepositoryConstraintViolationException;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.data.rest.webmvc.RepositoryRestExceptionHandler;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.hateoas.Resource;
import org.springframework.http.ResponseEntity;
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

    @ResponseBody
    @RequestMapping(value = "/weeks/{id}", method = RequestMethod.PUT)
    public ResponseEntity<?> saveWeek(@PathVariable("id") String id, @RequestBody Week week, BindingResult bindingResult) {
        ValidationUtils.invokeValidator(validator, week, bindingResult);
        if (bindingResult.hasErrors()) {
            throw new RepositoryConstraintViolationException(bindingResult);
        }
        ViewModel.HashId hashId = new ViewModel.HashId(id);
        if (weekRepository.findOne(hashId) == null) {
            throw new ResourceNotFoundException();
        }
        week.setHashId(hashId);
        week = weekRepository.save(week);
        return ResponseEntity.ok(new Resource<>(week));
    }

}
