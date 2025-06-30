package org.example.event;

import org.example.model.Space;
import org.springframework.context.ApplicationEvent;

public class SpaceAddedEvent extends ApplicationEvent {

    private final Space space;

    public SpaceAddedEvent(Object source, Space space) {
        super(source);
        this.space = space;
    }

    public Space getSpace() {
        return space;
    }
}
