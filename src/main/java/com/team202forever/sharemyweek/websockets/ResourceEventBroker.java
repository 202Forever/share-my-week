package com.team202forever.sharemyweek.websockets;

import com.team202forever.sharemyweek.data.models.Event;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.core.annotation.HandleAfterCreate;
import org.springframework.data.rest.core.annotation.RepositoryEventHandler;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
@RepositoryEventHandler
public class ResourceEventBroker {

    @Autowired
    private SimpMessagingTemplate template;

    @HandleAfterCreate
    public void sendEvent(Event event) {
        template.convertAndSend("/topic/refreshEvents", "");
    }
}
