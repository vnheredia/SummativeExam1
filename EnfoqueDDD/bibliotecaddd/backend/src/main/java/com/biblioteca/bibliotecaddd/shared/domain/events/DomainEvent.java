package com.biblioteca.bibliotecaddd.shared.domain.events;

import java.time.LocalDateTime;

public interface DomainEvent {
    LocalDateTime ocurredOn();
    String getEventId();
    String getAggregateId();
    String getEventType();
}