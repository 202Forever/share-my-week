package com.team202forever.sharemyweek.data.mvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.team202forever.sharemyweek.AbstractSpringTests;
import com.team202forever.sharemyweek.data.models.*;
import com.team202forever.sharemyweek.data.processors.WeekProcessor;
import com.team202forever.sharemyweek.data.repository.EventRepository;
import com.team202forever.sharemyweek.data.repository.WeekRepository;
import com.team202forever.sharemyweek.manager.EmailNotificationManager;
import lombok.Data;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;
import org.subethamail.wiser.Wiser;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

public class EventApiTests extends AbstractSpringTests {

    @Autowired
    private WebApplicationContext webApplicationContext;

    private Wiser wiser;
    private MockMvc mockMvc;

    @Autowired
    private WeekRepository weekRepository;

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private WeekProcessor weekProcessor;

    @Autowired
    private EmailNotificationManager emailNotificationManager;

    private List<Week> weeks;
    private List<Event> events;

    @Before
    public void setup() throws Exception {
        wiser = new Wiser(2525);
        wiser.start();
        mockMvc = webAppContextSetup(webApplicationContext).build();
        weeks = importJson(WeekCollection.class, "week", "week.json").getWeeks();
        weeks = weekRepository.insert(weeks);
        for (Week week : weeks) {
            weekProcessor.preprocess(week);
            emailNotificationManager.testEmailServer(week);
            emailNotificationManager.newWeekEmail(week);
        }

        events = new ArrayList<>();
        for (Week week : weeks) {
            WeekUser user = week.getUsers().iterator().next();
            Event event = new Event();
            event.setTitle("Event");
            DateTimeRange dateTimeRange = new DateTimeRange();
            dateTimeRange.setStart(new Date());
            dateTimeRange.setEnd(new Date());
            event.setDateTimeRange(dateTimeRange);
            event.setWeekId(week.getHashId().toString());
            event.setOwnerId(user.getUserInfo().getHashId().toString());
            events.add(event);
        }
        eventRepository.insert(events);
    }

    @After
    public void tearDown() {
        wiser.stop();
    }

    @Test
    public void postNegative() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        Week week = weeks.get(0);
        EventWrapper event = new EventWrapper();
        event.setTitle("Event");
        DateTimeRange dateTimeRange = new DateTimeRange();
        dateTimeRange.setStart(new Date());
        dateTimeRange.setEnd(new Date());
        event.setDateTimeRange(dateTimeRange);
        event.setWeekId(week.getHashId().toString());
        mockMvc.perform(post("/api/events")
                .content(objectMapper.writeValueAsString(event))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

        event.setWeekId(null);
        mockMvc.perform(post("/api/events")
                .content(objectMapper.writeValueAsString(event))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void postPositive() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        Week week = weeks.get(0);
        WeekUser user = week.getUsers().iterator().next();
        EventWrapper event = new EventWrapper();
        event.setTitle("Event");
        DateTimeRange dateTimeRange = new DateTimeRange();
        dateTimeRange.setStart(new Date());
        dateTimeRange.setEnd(new Date());
        event.setDateTimeRange(dateTimeRange);
        event.setWeekId(week.getHashId().toString());
        event.setOwnerId(user.getUserInfo().getHashId().toString());
        mockMvc.perform(post("/api/events?userId=" + user.getUserInfo().getHashId())
                .content(objectMapper.writeValueAsString(event))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }

    @Test
    public void putNegative() throws Exception {
        Event stored = events.get(0);
        EventWrapper event = new EventWrapper();
        event.setTitle("Modified Event");
        DateTimeRange dateTimeRange = new DateTimeRange();
        dateTimeRange.setStart(new Date());
        dateTimeRange.setEnd(new Date());
        event.setDateTimeRange(dateTimeRange);
        event.setWeekId(stored.getWeekId().toString());
        event.setOwnerId(stored.getOwnerId().toString());
        ObjectMapper objectMapper = new ObjectMapper();

        mockMvc.perform(put("/api/events")
                .content(objectMapper.writeValueAsString(event))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isMethodNotAllowed());

        // fakeId
        mockMvc.perform(put("/api/events/fakeId?userId=fakeUserId")
                .content(objectMapper.writeValueAsString(event))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

        // no user id
        mockMvc.perform(put("/api/events/" + stored.getHashId())
                .content(objectMapper.writeValueAsString(event))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

        // fake user id
        mockMvc.perform(put("/api/events/" + stored.getHashId() + "?userId=fakeId")
                .content(objectMapper.writeValueAsString(event))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }

    @Test
    public void putPositive() throws Exception {
        Event stored = events.get(0);
        EventWrapper event = new EventWrapper();
        event.setTitle("Modified Event");
        DateTimeRange dateTimeRange = new DateTimeRange();
        dateTimeRange.setStart(new Date());
        dateTimeRange.setEnd(new Date());
        event.setDateTimeRange(dateTimeRange);
        event.setWeekId(stored.getWeekId().toString());
        event.setOwnerId(stored.getOwnerId().toString());
        ObjectMapper objectMapper = new ObjectMapper();
        mockMvc.perform(put("/api/events/" + stored.getHashId() + "?userId=" + stored.getOwnerId())
                .content(objectMapper.writeValueAsString(event))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void deleteNegative() throws Exception {
        Event event = events.get(0);
        // no user id
        mockMvc.perform(delete("/api/events/" + event.getHashId()))
                .andExpect(status().isBadRequest());

        // fake user id
        mockMvc.perform(delete("/api/events/" + event.getHashId() + "?userId=fakeId"))
                .andExpect(status().isForbidden());
    }

    @Test
    public void deletePositive() throws Exception {
        Event event = events.get(0);
        mockMvc.perform(delete("/api/events/" + event.getHashId() + "?userId=" + event.getOwnerId()))
                .andExpect(status().isNoContent());
    }

    @Data
    private static class EventWrapper {

        public String title;

        private String description;

        private float maxBudget;

        private float minBudget;

        private DateTimeRange dateTimeRange;

        private String eventUrl;

        private String weekId;

        private String ownerId;
    }
}
