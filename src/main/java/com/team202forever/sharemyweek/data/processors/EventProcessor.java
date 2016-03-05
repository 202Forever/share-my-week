package com.team202forever.sharemyweek.data.processors;

import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceProcessor;
import org.springframework.social.facebook.api.Event;
import org.springframework.stereotype.Component;

@Component
public class EventProcessor implements ResourceProcessor<Resource<Event>> {

    @Override
    public Resource<Event> process(Resource<Event> resource) {
        return resource;
    }
}
