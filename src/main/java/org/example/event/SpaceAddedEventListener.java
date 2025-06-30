package org.example.event;

import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class SpaceAddedEventListener implements ApplicationListener<SpaceAddedEvent> {

    @Override
    public void onApplicationEvent(SpaceAddedEvent event) {
        System.out.println("Event: A new space has been added: " +
                event.getSpace().getType() + ", price: " + event.getSpace().getPrice());
    }
}
