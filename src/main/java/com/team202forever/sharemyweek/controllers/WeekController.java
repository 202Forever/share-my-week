package com.team202forever.sharemyweek.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/weeks")
public class WeekController {

	@RequestMapping("/{id}")
	public String getIndex(@PathVariable("id") String id) {
        return "index";
    }
	
    @RequestMapping(value = "/{id}?userId={userid}", method = RequestMethod.GET)
    public String getIndex(@PathVariable("id") String id, @RequestParam(value="userid", required=true) String userId) {
        return "index";
    }

}
