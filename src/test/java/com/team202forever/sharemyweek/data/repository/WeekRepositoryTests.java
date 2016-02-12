package com.team202forever.sharemyweek.data.repository;


import com.team202forever.sharemyweek.ShareMyWeekApplicationTests;
import com.team202forever.sharemyweek.data.models.WeekCollection;
import org.junit.Before;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;

public class WeekRepositoryTests extends ShareMyWeekApplicationTests {

    @Autowired
    private WeekRepository weekRepository;

    private WeekCollection weekCollection;

    @Before
    public void setup() throws IOException {
        weekCollection = importJson(WeekCollection.class, "week", "week.json");
    }
}
