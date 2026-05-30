package application.messaging;

import application.events.ReleaseDateUpdatedEvent;

public interface EventPublisher {
    void publish(ReleaseDateUpdatedEvent event);
}
