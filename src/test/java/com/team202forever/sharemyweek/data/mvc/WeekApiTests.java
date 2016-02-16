package com.team202forever.sharemyweek.data.mvc;

import static org.junit.Assert.*;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.sun.mail.iap.ResponseInputStream;
import com.team202forever.sharemyweek.data.models.User;
import com.team202forever.sharemyweek.data.models.Week;
import com.team202forever.sharemyweek.data.models.WeekCollection;
import com.team202forever.sharemyweek.data.repository.WeekRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;
import org.subethamail.wiser.Wiser;

import java.io.IOException;
import java.util.List;

public class WeekApiTests extends AbstractApiTests {

    @Autowired
    private WebApplicationContext webApplicationContext;

    private Wiser wiser;
    private MockMvc mockMvc;

    @Autowired
    private WeekRepository weekRepository;

    @Before
    public void setup() throws IOException {
        wiser = new Wiser(2525);
        wiser.start();
        mockMvc = webAppContextSetup(webApplicationContext).build();
        weekRepository.save(importJson(WeekCollection.class, "week", "week.json").getWeeks());
    }

    @After
    public void tearDown() {
        wiser.stop();
    }

    @Test
    public void fetchWeeks() throws Exception {
        List<Week> weeks = weekRepository.findAll();
        MockHttpServletResponse response = mockMvc.perform(get("/api/weeks")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn().getResponse();
        String json = response.getContentAsString();
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(json);
        JsonNode embedded = jsonNode.get("_embedded");
        WeekCollection weekCollection = readJson(WeekCollection.class, embedded.toString());
        assertEquals(weeks.size(), weekCollection.getWeeks().size());
    }

    @Test
    public void postWeekNegative() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        Week week = new Week();
        mockMvc.perform(post("/api/weeks")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(week)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void postWeekPositive() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        Week week = new Week();
        User user = new User();
        user.setEmail("test3@sharemyweek.com");
        week.getUsers().add(user);

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
        JsonNode link = links.get("self");
        JsonNode href = link.get("href");
        mockMvc.perform(get(href.asText())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn().getResponse();
    }
    
    // Deletes JSON with matching email
    @Test
    public void deleteUserByEmail() throws Exception {
         List<Week> weeks = weekRepository.findAll();
         Week week = weeks.get(0);
         int size = weeks.size();
         MockHttpServletResponse response = mockMvc.perform(delete("/api/weeks/" + week.getHashId().toString())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
        		.andExpect(status().isNoContent())
                .andReturn()
                .getResponse();
         weeks = weekRepository.findAll();
         assertEquals(size - 1, weeks.size());
    }
    
  //Adds User with matching email
  	@Test
  	public void putUserByEmail() throws Exception {
  		List<Week> weeks = weekRepository.findAll();
          Week week = weeks.get(0);
          int size = weeks.size();
          MockHttpServletResponse response = mockMvc.perform(put("/api/weeks/" + week.getHashId().toString())
                 .contentType(MediaType.APPLICATION_JSON)
                 .accept(MediaType.APPLICATION_JSON))
         		.andExpect(status().isAccepted())
                 .andReturn()
                 .getResponse();
          weeks = weekRepository.findAll();
          assertEquals(size + 1, weeks.size());
  	}
}

	
