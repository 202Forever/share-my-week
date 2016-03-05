package com.team202forever.sharemyweek.data.processors;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;

import com.team202forever.sharemyweek.controllers.SpringDataRestApiController;
import com.team202forever.sharemyweek.controllers.WeekController;
import com.team202forever.sharemyweek.data.models.User;
import com.team202forever.sharemyweek.data.models.ViewModel;
import com.team202forever.sharemyweek.data.models.Week;
import com.team202forever.sharemyweek.data.models.WeekUser;
import com.team202forever.sharemyweek.data.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.core.annotation.HandleBeforeCreate;
import org.springframework.data.rest.core.annotation.RepositoryEventHandler;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceProcessor;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.MalformedURLException;
import java.net.URL;

@Component
@RepositoryEventHandler
public class WeekProcessor implements ResourceProcessor<Resource<Week>> {

    private final Logger logger = LoggerFactory.getLogger(WeekProcessor.class);

    @Autowired
    private RepositoryLinksProcessor repositoryLinksProcessor;

    @Autowired
    private UserRepository userRepository;

    @HandleBeforeCreate
    public void preprocess(Week week) {
        for (WeekUser weekUser : week.getUsers()) {
        	User userInfo = weekUser.getUserInfo();
            User storedInfo = userRepository.findByEmail(userInfo.getEmail());
            if (storedInfo != null) {
            	weekUser.setUserInfo(storedInfo);
            }
        }
    }

    @Override
    public Resource<Week> process(Resource<Week> resource) {
        Week week = resource.getContent();
        if (week.getLink("page") == null) {
            resource.add(linkToWeek(week, "page"));
        }
        try {
            resource.add(linkToWeekEvents(week));
        } catch (MalformedURLException e) {
            logger.warn("Failed to create events link for week", e);
        }
        resource.getLinks().addAll(week.getLinks());
        return resource;
    }
    
    public Link linkToWeek(Week week, String rel) {
    	return linkTo(WeekController.class).slash(week.getHashId().toString()).withRel(rel);
    }

    public Link linkToWeekEvents(Week week) throws MalformedURLException {
        UriComponentsBuilder uriComponentsBuilder = linkTo(SpringDataRestApiController.class).slash("weeks").slash(week.getHashId().toString()).slash("events").toUriComponentsBuilder();
        URL url = new URL(uriComponentsBuilder.toUriString());
        uriComponentsBuilder.replacePath(repositoryLinksProcessor.getBasePath() + url.getPath());
        return new Link(uriComponentsBuilder.toUriString(), "events");
    }
    
    public Link linkToWeek(Week week, User user, String rel) throws NoSuchMethodException, SecurityException {
    	return linkTo(WeekController.class.getMethod("getIndex", String.class, String.class), week.getHashId().toString(), user.getHashId()).withRel(rel);
    }
}
