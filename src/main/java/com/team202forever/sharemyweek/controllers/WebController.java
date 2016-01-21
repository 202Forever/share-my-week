package com.team202forever.sharemyweek.controllers;

import com.team202forever.sharemyweek.data.DateTimeRange;
import com.team202forever.sharemyweek.data.User;
import com.team202forever.sharemyweek.data.Week;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;

@RestController
public class WebController {

    @RequestMapping(value = "/week", method = RequestMethod.POST, produces = "application/json")
    @ResponseBody
    public ResponseEntity addWeek() {
        Week week = new Week();
        week.setId(1L);
        User user = new User();
        user.setId(2L);
        user.setDateTimeRange(Collections.singletonList(new DateTimeRange()));
        week.setUsers(Collections.singletonList(user));
        return new ResponseEntity<>(week, HttpStatus.OK);
    }

}
