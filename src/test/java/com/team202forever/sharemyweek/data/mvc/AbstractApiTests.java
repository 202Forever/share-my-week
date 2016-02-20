package com.team202forever.sharemyweek.data.mvc;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.team202forever.sharemyweek.ShareMyWeekApplication;
import com.team202forever.sharemyweek.config.FongoConfiguration;
import com.team202forever.sharemyweek.config.RepositoryApiConfigurer;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.io.FileReader;
import java.io.IOException;
import java.io.StringReader;

@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = {ShareMyWeekApplication.class, RepositoryApiConfigurer.class, FongoConfiguration.class})
public  abstract class AbstractApiTests {

    protected <T> T importJson(Class<T> clazz, String collection, String filename) throws IOException {
        JsonParser jsonParser = new JsonFactory().createParser(new FileReader("src/test/resources/" + filename));
        return new ObjectMapper().readValue(jsonParser, clazz);
    }

    protected <T> T readJson(Class<T> clazz, String json) throws IOException {
        JsonParser jsonParser = new JsonFactory().createParser(new StringReader(json));
        return new ObjectMapper().readValue(jsonParser, clazz);
    }

}
