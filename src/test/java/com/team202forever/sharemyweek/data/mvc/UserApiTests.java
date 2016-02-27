package com.team202forever.sharemyweek.data.mvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.team202forever.sharemyweek.AbstractSpringTests;
import com.team202forever.sharemyweek.data.models.User;
import com.team202forever.sharemyweek.data.models.UserCollection;
import com.team202forever.sharemyweek.data.repository.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import java.io.IOException;

public class UserApiTests extends AbstractSpringTests {

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @Autowired
    UserRepository userRepository;

    @Before
    public void setup() throws IOException {
        mockMvc = webAppContextSetup(webApplicationContext).build();
        userRepository.insert(importJson(UserCollection.class, "user", "user.json").getUsers());
    }

    @Test
    public void deleteUser() throws Exception {
        User user = userRepository.findAll().get(0);
        mockMvc.perform(delete("/api/users/" + user.getHashId()))
                .andExpect(status().isMethodNotAllowed());

        ObjectMapper objectMapper = new ObjectMapper();
        mockMvc.perform(delete("/api/users")
                .content(objectMapper.writeValueAsString(user))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isMethodNotAllowed());
    }

    @Test
    public void deleteUsers() throws Exception {
        mockMvc.perform(delete("/api/users"))
                .andExpect(status().isMethodNotAllowed());
    }

    @Test
    public void fetchUsers() throws Exception {
        mockMvc.perform(get("/api/users")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isMethodNotAllowed());
        Pageable pageable = null;
        userRepository.findAll(pageable);
    }

    @Test
    public void getUser() throws Exception {
        User user = userRepository.findAll().get(0);
        mockMvc.perform(get("/api/users/" + user.getHashId())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void postUser() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        User user = new User();
        user.setEmail("test1@sharemyweek.com");
        mockMvc.perform(post("/api/users")
                .content(objectMapper.writeValueAsString(user))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isMethodNotAllowed());
    }

    @Test
    public void putUserPositive() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        User user = userRepository.findAll().get(0);
        mockMvc.perform(put("/api/users/" + user.getHashId())
                .content(objectMapper.writeValueAsString(user))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void putUserNegative() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        User user = userRepository.findAll().get(0);
        user.setEmail(null);
        mockMvc.perform(put("/api/users/" + user.getHashId())
                .content(objectMapper.writeValueAsString(user))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

        //fake user
        User fake = new User();
        fake.setEmail("fake@email.com");
        mockMvc.perform(put("/api/users/fakeId")
                .content(objectMapper.writeValueAsString(fake))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}
