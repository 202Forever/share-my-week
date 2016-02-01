package com.team202forever.sharemyweek.controllers;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.google.gson.Gson;


@RestController
public class WebController {
	
	// Example using GSON library
	// This is the object that'll be converted to JSON via GSON
	public class DataObject{
		public String city = "Pomona",state = "CA",country = "USA";
	}
	
	DataObject obj = new DataObject();
	Gson gson = new Gson();
	
	// Convert java object to JSON format,
	// and returned as JSON formatted string
	String json = gson.toJson(obj);

   // HTTP GET outputs java object values in JSON format
	@RequestMapping(value = "/shareMyWeek/gson", method = RequestMethod.GET)
	String newString() {
		// run the application locally to check your changes
		// with the URL: http://localhost:8080/
		return json;
	}
}
