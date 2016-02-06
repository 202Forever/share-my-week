package com.team202forever.sharemyweek.data.processors;

import com.team202forever.sharemyweek.controllers.WeekController;
import com.team202forever.sharemyweek.data.models.ViewModel;
import com.team202forever.sharemyweek.data.models.Week;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceProcessor;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.stereotype.Component;

@Component
public class ViewModelProcessor implements ResourceProcessor<Resource<? extends ViewModel>> {
    @Override
    public Resource<? extends ViewModel> process(Resource<? extends ViewModel> resource) {
        ViewModel model = resource.getContent();
        if (model instanceof Week) {
            resource.add(ControllerLinkBuilder.linkTo(WeekController.class).slash(resource.getContent().getHashId().toString()).withRel("page"));
        }
        return resource;
    }
}
