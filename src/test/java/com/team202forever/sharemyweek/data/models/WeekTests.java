package com.team202forever.sharemyweek.data.models;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class WeekTests {

    private Week week;
    private WeekUser weekUser;

    @Before
    public void setup() {
        weekUser = new WeekUser();
        User user = new User();
        user.setEmail("test1@sharemyweek.com");
        weekUser.setUserInfo(user);
        week = new Week();
        week.getUsers().add(weekUser);
    }

    @Test
    public void notEquals() {
        Week week1 = new Week();
        Week week2 = new Week();
        assertTrue(!week1.equals(week2));
    }

    @Test
    public void equals() {
        HashId hashId = new HashId();
        Week week1 = new Week();
        week1.setHashId(hashId);
        Week week2 = new Week();
        week2.setHashId(hashId);
        assertTrue(week1.equals(week2));
    }

    @Test
    public void removeUser() {
        week.getUsers().remove(weekUser);
        assertTrue(week.getUsers().isEmpty());
    }

    @Test
    public void removeModifiedUser() {
        weekUser.setRole(WeekUserRole.OWNER); //changed!
        week.getUsers().remove(weekUser);
        assertTrue(week.getUsers().isEmpty());
    }

}
