package com.team202forever.sharemyweek.data.processors;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;

import com.team202forever.sharemyweek.controllers.WeekController;
import com.team202forever.sharemyweek.data.models.User;
import com.team202forever.sharemyweek.data.models.ViewModel;
import com.team202forever.sharemyweek.data.models.Week;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceProcessor;
import org.springframework.stereotype.Component;

@Component
public class ViewModelProcessor implements ResourceProcessor<Resource<? extends ViewModel>> {
	
    @Override
    public Resource<? extends ViewModel> process(Resource<? extends ViewModel> resource) {
        ViewModel model = resource.getContent();
        if (model instanceof Week) {
            if (model.getLink("page") == null) {
                resource.add(linkToWeek((Week) model, "page"));
            }
        }
        resource.getLinks().addAll(model.getLinks());
        return resource;
    }
    
    public static Link linkToWeek(Week week, String rel) {
    	return linkTo(WeekController.class).slash(week.getHashId().toString()).withRel(rel);
    }
    
    public static Link linkToWeek(Week week, User user, String rel) throws NoSuchMethodException, SecurityException {
    	return linkTo(WeekController.class.getMethod("getIndex", String.class, String.class), week.getHashId().toString(), user.getHashId()).withRel(rel);
    }
}
