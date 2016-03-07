package com.team202forever.sharemyweek.data.mvc;

import static org.junit.Assert.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.*;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.team202forever.sharemyweek.AbstractSpringTests;
import com.team202forever.sharemyweek.data.models.*;
import com.team202forever.sharemyweek.data.processors.WeekProcessor;
import com.team202forever.sharemyweek.data.repository.UserRepository;
import com.team202forever.sharemyweek.data.repository.WeekRepository;
import com.team202forever.sharemyweek.manager.EmailNotificationManager;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;
import org.subethamail.wiser.Wiser;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class WeekApiTests extends AbstractSpringTests {

    @Autowired
    private WebApplicationContext webApplicationContext;

    private Wiser wiser;
    private MockMvc mockMvc;

    @Autowired
    private WeekRepository weekRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private WeekProcessor weekProcessor;

    @Autowired
    private EmailNotificationManager emailNotificationManager;

    @Before
    public void setup() throws Exception {
        wiser = new Wiser(2525);
        wiser.start();
        mockMvc = webAppContextSetup(webApplicationContext).build();
        userRepository.insert(importJson(UserCollection.class, "user", "user.json").getUsers());
        List<Week> weeks = importJson(WeekCollection.class, "week", "week.json").getWeeks();
        weeks = weekRepository.insert(weeks);
        for (Week week : weeks) {
            weekProcessor.preprocess(week);
            emailNotificationManager.testEmailServer(week);
            emailNotificationManager.newWeekEmail(week);
        }
    }

    @After
    public void tearDown() {
        wiser.stop();
    }

    @Test
    public void fetchWeeks() throws Exception {
        mockMvc.perform(get("/api/weeks")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isMethodNotAllowed())
                .andReturn().getResponse();
    }

    @Test
    public void postWeekNegative() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        Week week = new Week();
        mockMvc.perform(post("/api/weeks")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(week)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void postWeekPositive() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        Week week = new Week();
        WeekUser user = new WeekUser();
        User userInfo = new User();
        userInfo.setEmail("test3@sharemyweek.com");
        user.setUserInfo(userInfo);
        week.getUsers().add(user);
        List<User> users = userRepository.findAll();
        for (User stored : users) {
            WeekUser storedUser = new WeekUser();
            storedUser.setUserInfo(stored);
            week.getUsers().add(storedUser);
        }

        // post
        String json = objectMapper.writeValueAsString(week);
        MockHttpServletResponse response = mockMvc.perform(post("/api/weeks")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isCreated())
                .andReturn().getResponse();

        //follow links to verify entity was persisted
        json = response.getContentAsString();
        JsonNode jsonNode = objectMapper.readTree(json);
        JsonNode links = jsonNode.get("_links");
        JsonNode link = links.get("page");
        JsonNode href = link.get("href");
        mockMvc.perform(get(href.asText()))
                .andExpect(status().isOk());

        //test ownership
        userInfo = userRepository.findByEmail(userInfo.getEmail());
        assertTrue(!userInfo.getWeekIds().isEmpty());
        week = weekRepository.findOne(new HashId(userInfo.getWeekIds().iterator().next()));
        assertTrue(week.getUsers().size() > 1);
        for (WeekUser weekUser : week.getUsers()) {
            weekUser.setRole(WeekUserRole.USER);
        }
        weekRepository.save(week);
        Iterator<WeekUser> iter = week.getUsers().iterator();
        iter.next();
        iter.remove();
        userInfo = iter.next().getUserInfo();
        mockMvc.perform(put("/api/weeks/" + week.getHashId() + "?userId=" + userInfo.getHashId())
                .content(objectMapper.writeValueAsString(week))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }

    @Test
    public void deleteWeeks() throws Exception {
        mockMvc.perform(delete("/api/weeks"))
                .andExpect(status().isMethodNotAllowed());
    }

    @Test
    public void deleteWeekNegative() throws Exception {
        mockMvc.perform(delete("/api/weeks/fakeId?userId=fakeId")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

        List<Week> weeks = weekRepository.findAll();
        Week week = weeks.get(0);
        mockMvc.perform(delete("/api/weeks/" + week.getHashId() + "?userId=fakeId")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }

    @Test
    public void deleteWeekPositive() throws Exception {
         List<Week> weeks = weekRepository.findAll();
         Week week = weeks.get(0);
         int size = weeks.size();
         mockMvc.perform(delete("/api/weeks/" + week.getHashId() + "?userId=" + week.getUsers().iterator().next().getUserInfo().getHashId()))
        		.andExpect(status().isNoContent());
         weeks = weekRepository.findAll();
         assertEquals(size - 1, weeks.size());
    }

    @Test
    public void putWeekNegative() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();

        //fake user test
        Week fake = new Week();
        WeekUser weekUser = new WeekUser();
        User user = new User();
        user.setEmail("fake@email.com");
        weekUser.setUserInfo(user);
        fake.getUsers().add(weekUser);
        mockMvc.perform(put("/api/weeks/fakeId?userId=fakeUserId")
                .content(objectMapper.writeValueAsString(fake))
                .contentType(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

        //missing user id
        List<Week> weeks = weekRepository.findAll();
        Week week = weeks.get(0);
        mockMvc.perform(put("/api/weeks/" + week.getHashId())
                .content(objectMapper.writeValueAsString(week))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

        //empty email request
        List<WeekUser> users = new ArrayList<>(week.getUsers());
        user = users.get(0).getUserInfo();
        user.setEmail("");
        mockMvc.perform(put("/api/weeks/" + week.getHashId() + "?userId=" + user.getHashId())
                .content(objectMapper.writeValueAsString(week))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

        //TODO change to non admin user
        //attempt to modify different user info
//        for (Week weekModel : weeks) {
//            if (weekModel.getUsers().size() > 1) {
//                week = weekModel;
//                users = new ArrayList<>(week.getUsers());
//                user = users.get(1).getUserInfo();
//                user.setEmail("fake@email.com");
//                user.setMaxBudget(0.0f);
//                break;
//            }
//        }
//        user = users.get(0).getUserInfo();
//        user.setEmail("my@mail.com");
//        mockMvc.perform(put("/api/weeks/" + week.getHashId() + "?userId=" + user.getHashId())
//                .content(objectMapper.writeValueAsString(week))
//                .contentType(MediaType.APPLICATION_JSON)
//                .accept(MediaType.APPLICATION_JSON))
//                .andExpect(status().isForbidden());

        //not allowed request
        mockMvc.perform(put("/api/weeks/" + week.getHashId().toString() + "?userId=" + user.getHashId())
                .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                .accept(MediaType.APPLICATION_FORM_URLENCODED_VALUE))
                .andExpect(status().isUnsupportedMediaType());
    }

  	@Test
  	public void putWeekPositive() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        List<Week> weeks = weekRepository.findAll();
        Week week = weeks.get(0);
        int size = weeks.size();
        mockMvc.perform(put("/api/weeks/" + week.getHashId() + "?userId=" + week.getUsers().iterator().next().getUserInfo().getHashId())
                .content(objectMapper.writeValueAsString(week))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        weeks = weekRepository.findAll();
        assertEquals(size, weeks.size());
    }
}

	
