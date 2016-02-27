package com.team202forever.sharemyweek.controllers;

import com.team202forever.sharemyweek.data.models.HashId;
import com.team202forever.sharemyweek.data.repository.WeekRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/weeks")
public class WeekController {

    @Autowired
    private WeekRepository weekRepository;

	@RequestMapping("/{id}")
	public String getIndex(@PathVariable("id") String id) {
        if (weekRepository.findOne(new HashId(id)) == null) {
            throw new ResourceNotFoundException();
        }
        return "index";
    }
	
    @RequestMapping(value = "/{id}?userId={userid}", method = RequestMethod.GET)
    public String getIndex(@PathVariable("id") String id, @RequestParam(value="userid", required=true) String userId) {
        return "index";
    }

}
