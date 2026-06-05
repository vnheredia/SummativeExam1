package com.biblioteca.bibliotecaddd.shared.infrastructure.events;

import com.biblioteca.bibliotecaddd.shared.domain.events.DomainEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

/**
 * Adaptador para publicar eventos de dominio usando Spring's ApplicationEventPublisher.
 */
@Component
public class DomainEventPublisher {
    private final ApplicationEventPublisher springPublisher;

    public DomainEventPublisher(ApplicationEventPublisher springPublisher) {
        this.springPublisher = springPublisher;
    }

    public void publish(DomainEvent event) {
        springPublisher.publishEvent(event);
    }
}