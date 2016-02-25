package com.team202forever.sharemyweek.data.models;

import static org.junit.Assert.*;

import org.junit.Test;

public class WeekTests {

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
}
