package org.example.service;

import org.example.event.SpaceAddedEvent;
import org.example.model.Space;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Component
public class CoworkingService implements ApplicationEventPublisherAware {

    private final List<Space> spaces = new ArrayList<>();
    private ApplicationEventPublisher eventPublisher;

    public void init() {
        System.out.println("CoworkingService bean initialized.");
    }

    public void cleanup() {
        System.out.println("CoworkingService bean destroyed.");
    }

    public void addSpace(String type, BigDecimal price) {
        Space space = new Space(type, price);
        spaces.add(space);
        System.out.println("Space added: " + space);

        if (eventPublisher != null) {
            eventPublisher.publishEvent(new SpaceAddedEvent(this, space));
        }
    }

    public void listSpaces() {
        if (spaces.isEmpty()) {
            System.out.println("There are no available spaces.");
        } else {
            System.out.println("List of spaces:");
            spaces.forEach(System.out::println);
        }
    }

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher publisher) {
        this.eventPublisher = publisher;
    }
}
