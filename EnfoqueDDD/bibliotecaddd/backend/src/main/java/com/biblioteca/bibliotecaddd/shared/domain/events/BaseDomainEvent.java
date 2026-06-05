package com.biblioteca.bibliotecaddd.shared.domain.events;

import java.time.LocalDateTime;
import java.util.UUID;

public abstract class BaseDomainEvent implements DomainEvent {
    private final String eventId;
    private final LocalDateTime ocurredOn;
    private final String aggregateId;

    public BaseDomainEvent(String aggregateId) {
        this.eventId = UUID.randomUUID().toString();
        this.ocurredOn = LocalDateTime.now();
        this.aggregateId = aggregateId;
    }

    @Override
    public LocalDateTime ocurredOn() {
        return ocurredOn;
    }

    @Override
    public String getEventId() {
        return eventId;
    }

    @Override
    public String getAggregateId() {
        return aggregateId;
    }

    public abstract String getEventType();
}