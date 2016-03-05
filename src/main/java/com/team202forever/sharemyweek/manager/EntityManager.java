package com.team202forever.sharemyweek.manager;

import com.team202forever.sharemyweek.data.models.Event;
import com.team202forever.sharemyweek.data.models.WeekUser;
import com.team202forever.sharemyweek.data.repository.UserRepository;
import com.team202forever.sharemyweek.data.repository.WeekRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.stereotype.Component;

@Component
public class EntityManager {

    private WeekRepository weekRepository;

    private UserRepository userRepository;

    @Autowired
    public void setWeekRepository(WeekRepository weekRepository) {
        this.weekRepository = weekRepository;
        Event.weekRepository = weekRepository;
    }

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
        WeekUser.userRepository = userRepository;
        Event.userRepository = userRepository;
    }

    public UserRepository getUserRepository() {
        return userRepository;
    }
}
