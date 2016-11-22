package com.team202forever.sharemyweek.controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.team202forever.sharemyweek.data.models.*;
import com.team202forever.sharemyweek.data.repository.EventRepository;
import com.team202forever.sharemyweek.data.repository.UserRepository;
import com.team202forever.sharemyweek.data.repository.WeekRepository;
import com.team202forever.sharemyweek.data.thirdparty.eventbrite.EventbriteProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.core.RepositoryConstraintViolationException;
import org.springframework.data.rest.webmvc.PersistentEntityResource;
import org.springframework.data.rest.webmvc.PersistentEntityResourceAssembler;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.hateoas.PagedResources;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.SmartValidator;
import org.springframework.validation.ValidationUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import javax.ws.rs.BadRequestException;
import javax.ws.rs.ForbiddenException;
import java.io.IOException;
import java.net.URLDecoder;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RepositoryRestController
@RequestMapping("/events")
public class EventController {

    @Autowired
    private EventbriteProperties eventbriteProperties;

    @Autowired
    private WeekRepository weekRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private SimpMessagingTemplate template;

    @Autowired
    private SmartValidator validator;

    @ResponseBody
    @RequestMapping(value = {"/{id}", "/{id}?userId={userId}"}, method = RequestMethod.PUT)
    public PersistentEntityResource saveEvent(@PathVariable("id") String id, @RequestParam(value = "userId", required = true) String userId, @RequestBody Event event, BindingResult bindingResult, PersistentEntityResourceAssembler resourceAssembler) {
        if (userId == null) {
            throw new BadRequestException("A user id must be specified to update the event");
        }
        Event stored = eventRepository.findOne(new HashId(id));
        if (stored == null) {
            throw new ResourceNotFoundException();
        }
        User user = null;
        for (WeekUser weekUser : stored.getWeek().getUsers()) {
            if (weekUser.getUserInfo().getHashId().toString().equals(userId)) {
                user = weekUser.getUserInfo();
                break;
            }
        }
        if (user == null) {
            throw new ForbiddenException("The user is forbidden to update the event");
        }
        event.setHashId(stored.getHashId());
        event.setOwnerId(stored.getOwnerId().toString());
        event.setWeekId(stored.getWeekId().toString());
        ValidationUtils.invokeValidator(validator, event, bindingResult);
        if (bindingResult.hasErrors()) {
            throw new RepositoryConstraintViolationException(bindingResult);
        }
        event = eventRepository.save(event);
        sendEvents();
        return resourceAssembler.toResource(event);
    }

    @RequestMapping(value = {"/{id}", "/{id}?userId={userId}"}, method = RequestMethod.DELETE)
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void deleteEvent(@PathVariable( "id") String id, @RequestParam(value = "userId", required = true) String userId, PersistentEntityResourceAssembler resourceAssembler) {
        if (userId == null) {
            throw new BadRequestException("A user id must be specified to update the event");
        }
        Event stored = eventRepository.findOne(new HashId(id));
        if (stored == null) {
            throw new ResourceNotFoundException();
        }
        User user = null;
        for (WeekUser weekUser : stored.getWeek().getUsers()) {
            if (weekUser.getUserInfo().getHashId().toString().equals(userId)) {
                user = weekUser.getUserInfo();
                break;
            }
        }
        if (user == null) {
            throw new ForbiddenException("The user is forbidden to update the event");
        }
        eventRepository.delete(stored.getHashId());
        sendEvents();
    }

    /**
     * TODO
     *
     * Gets a list of events from Eventbrite. In the future reactor to use multiple online sources.
     *
     * This service does basic operations with RestTemplate. In the future it is preferred to use Spring Social integration with OAuth
     */
    @ResponseBody
    @RequestMapping(method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<PagedResources<Event>> getEvents(
            @RequestParam(value = "keywords", required = false) String keywords,
            @RequestParam(value = "city", required = false) String city,
            @RequestParam(value = "country", required = false) String country,
            @RequestParam(value = "start", required = false) String start,
            @RequestParam(value = "end", required = false) String end,
            @RequestParam(value = "budget", required = false) Float budget,
            @RequestParam(value = "sort", required = false) String sort,
            @RequestParam(value = "page", required = false) Integer page) throws IOException {
        ZonedDateTime startTime = null;
        if (start != null) {
            try {
                startTime = ZonedDateTime.parse(start);
            } catch (DateTimeParseException e) {
                throw new BadRequestException("Failed to interpret start date. Please use a ISO-8601 time format.");
            }
        }
        ZonedDateTime endTime = null;
        if (end != null) {
            try {
                endTime = ZonedDateTime.parse(end);
            } catch (DateTimeParseException e) {
                throw new BadRequestException("Failed to interpret end date. Please use a ISO-8601 time format.");
            }
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("YYYY-MM-dd'T'hh:mm:ss'Z'");
        RestTemplate restTemplate = new RestTemplate();
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("token", eventbriteProperties.getToken());
        params.add("q", keywords);
        params.add("location.address", city + ", " + country);
        params.add("location.within", "5mi");
        params.add("price", budget == null ? null : budget.toString());
        params.add("start_date.range_start", startTime == null ? null : startTime.format(formatter));
        params.add("start_date.range_end", end == null ? null : endTime.format(formatter));
        params.add("sort_by", sort);
        params.add("page", page == null ? null : page.toString());
        String response = restTemplate.getForObject(URLDecoder.decode(UriComponentsBuilder.fromHttpUrl(eventbriteProperties.getApiPath() + "/events/search/").queryParams(params).toUriString(), "UTF-8"), String.class);
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode rootNode = objectMapper.readTree(response);
        JsonNode eventsNode = rootNode.get("events");
        JsonNode pageNode = rootNode.get("pagination");
        long total = pageNode.get("object_count").asLong();
        long pageNumber = pageNode.get("page_number").asLong();
        long pageSize = pageNode.get("page_size").asLong();
        long pageCount = pageNode.get("page_count").asLong();
        List<Event> events = new ArrayList<>();
        if (eventsNode.isArray()) {
            for (JsonNode eventNode : eventsNode) {
                Event event = new Event();
                event.setTitle(eventNode.get("name").get("text").textValue());
                DateTimeRange dateTimeRange = new DateTimeRange();
                dateTimeRange.setStart(Date.from(ZonedDateTime.parse(eventNode.get("start").get("utc").textValue()).toInstant()));
                dateTimeRange.setEnd(Date.from(ZonedDateTime.parse(eventNode.get("end").get("utc").textValue()).toInstant()));
                event.setDateTimeRange(dateTimeRange);
                event.setDescription(eventNode.get("description").get("text").asText());
                event.setEventUrl(eventNode.get("url").asText());
                JsonNode logoNode = eventNode.get("logo");
                if (logoNode != null) {
                    JsonNode urlNode = logoNode.get("url");
                    if (urlNode != null) {
                        Image image = new Image();
                        image.setUrl(urlNode.asText());
                        image.setAspectRatio(logoNode.get("aspect_ratio").asDouble());
                        event.setImage(image);
                    }

                }
                events.add(event);
            }
        }
        return ResponseEntity.ok(new PagedResources<>(events, new PagedResources.PageMetadata(pageSize, pageNumber, total, pageCount)));
    }

    private void sendEvents() {
        template.convertAndSend("/topic/refreshEvents", "");
    }
}
