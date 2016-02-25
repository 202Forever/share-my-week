package com.team202forever.sharemyweek.data.models;

import static org.junit.Assert.*;

import org.junit.Test;

public class WeekUserTests {

    @Test
    public void equals() {
        WeekUser weekUser1 = new WeekUser();
        WeekUser weekUser2 = new WeekUser();
        assertTrue(weekUser1.equals(weekUser2));
    }
}
