package com.team202forever.sharemyweek.data.models;

import static org.junit.Assert.*;

import org.junit.Test;

public class UserTests {

    @Test
    public void notEquals() {
        User user1 = new User();
        User user2 = new User();
        assertTrue(!user1.equals(user2));
    }

    @Test
    public void equals() {
        HashId hashId = new HashId();
        User user1 = new User();
        user1.setHashId(hashId);
        User user2 = new User();
        user2.setHashId(hashId);
        assertTrue(user1.equals(user2));
    }
}
