package com.team202forever.sharemyweek.data.processors;

import com.team202forever.sharemyweek.data.models.User;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceProcessor;
import org.springframework.stereotype.Component;

@Component
public class UserProcessor implements ResourceProcessor<Resource<User>> {

    @Override
    public Resource<User> process(Resource<User> resource) {
        return resource;
    }
}
