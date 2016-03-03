package com.team202forever.sharemyweek.data.processors;

import com.team202forever.sharemyweek.controllers.EventController;
import com.team202forever.sharemyweek.controllers.SpringDataRestApiController;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.data.rest.webmvc.RepositoryLinksResource;
import org.springframework.hateoas.*;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;

@Component
@ConfigurationProperties("spring.data.rest")
@Setter
public class RepositoryLinksProcessor implements ResourceProcessor<RepositoryLinksResource> {

    private String basePath;

    private final Logger logger = LoggerFactory.getLogger(RepositoryLinksProcessor.class);

    @Override
    public RepositoryLinksResource process(RepositoryLinksResource resource) {
        if (resource.getLink("events") != null) {
            List<Link> links = new ArrayList<>(resource.getLinks());
            List<Link> newLinks = new ArrayList<>();
            Iterator<Link> iter = links.iterator();
            while(iter.hasNext()) {
                Link link = iter.next();
                try {
                    if (link.getRel().equals("events")) {
                        newLinks.add(linkToEvents());
                        iter.remove();
                    } else if (link.getRel().equals("weeks")) {
                        newLinks.add(linkToWeek());
                        iter.remove();
                    } else if (link.getRel().equals("users")) {
                        newLinks.add(linkToUser());
                        iter.remove();
                    }
                } catch (IOException e) {
                    logger.warn("Failed update Hal links", e);
                }
            }
            resource.removeLinks();
            resource.add(links);
            resource.add(newLinks);
        }
        return resource;
    }


    //TODO link to Spring PR??
    private Link linkToWeek() throws MalformedURLException {
//        UriComponentsBuilder uriComponentsBuilder = linkTo(methodOn(SpringDataRestApiController.class).saveWeek("", null, null, null, null)).toUriComponentsBuilder();
//        URL url = new URL(uriComponentsBuilder.toUriString());
//        uriComponentsBuilder.replacePath(basePath + url.getPath());
        return new Link(
                new UriTemplate(
                        basePath + "/weeks/" + "{id}",
                        new TemplateVariables(
                                new TemplateVariable("id", TemplateVariable.VariableType.PATH_VARIABLE)
                        )
                ),
                "weeks"
        );
    }

    //TODO refacotr
    private Link linkToUser() throws MalformedURLException {
        UriComponentsBuilder uriComponentsBuilder = linkTo(methodOn(SpringDataRestApiController.class).saveUser("", null, null, null)).toUriComponentsBuilder();
        URL url = new URL(uriComponentsBuilder.toUriString());
        uriComponentsBuilder.replacePath(basePath + url.getPath());
        return new Link(
            new UriTemplate(
                    uriComponentsBuilder.toUriString() + "{id}",
                    new TemplateVariables(
                        new TemplateVariable("id", TemplateVariable.VariableType.PATH_VARIABLE)
                    )
            ),
            "users"
        );
    }

    private Link linkToEvents() throws IOException {
        UriComponentsBuilder uriComponentsBuilder = linkTo(methodOn(EventController.class).getEvents(null, null, null, null, null, null, null, null)).toUriComponentsBuilder();
        URL url = new URL(uriComponentsBuilder.toUriString());
        uriComponentsBuilder.replacePath(basePath + url.getPath());
        return new Link(
            new UriTemplate(
                uriComponentsBuilder.toUriString(),
                new TemplateVariables( //TODO reflect on @QueryParam
                    new TemplateVariable("keywords", TemplateVariable.VariableType.REQUEST_PARAM),
                    new TemplateVariable("city", TemplateVariable.VariableType.REQUEST_PARAM),
                    new TemplateVariable("country", TemplateVariable.VariableType.REQUEST_PARAM),
                    new TemplateVariable("start", TemplateVariable.VariableType.REQUEST_PARAM),
                    new TemplateVariable("end", TemplateVariable.VariableType.REQUEST_PARAM),
                    new TemplateVariable("budget", TemplateVariable.VariableType.REQUEST_PARAM),
                    new TemplateVariable("sort", TemplateVariable.VariableType.REQUEST_PARAM),
                    new TemplateVariable("page", TemplateVariable.VariableType.REQUEST_PARAM)
                )
            ),
            "events"
        );
    }
}
