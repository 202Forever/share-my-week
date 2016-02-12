package com.team202forever.sharemyweek;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.team202forever.sharemyweek.config.FongoConfiguration;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.FileReader;
import java.io.IOException;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = {ShareMyWeekApplication.class, FongoConfiguration.class})
public class ShareMyWeekApplicationTests {

	@Autowired
	private MongoTemplate mongoTemplate;

	@Test
	public void contextLoads() {
	}

	protected <T> T importJson(Class<T> clazz, String collection, String filename) throws IOException {
		JsonParser jsonParser = new JsonFactory().createParser(new FileReader("src/test/resources/" + filename));
		return new ObjectMapper().readValue(jsonParser, clazz);
	}

}
