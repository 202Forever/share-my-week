package com.team202forever.sharemyweek.manager;

import com.team202forever.sharemyweek.data.models.WeekUser;
import com.team202forever.sharemyweek.data.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EntityManager {

    private UserRepository userRepository;

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
        WeekUser.userRepository = userRepository;
    }

    public UserRepository getUserRepository() {
        return userRepository;
    }
}
