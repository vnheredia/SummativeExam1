package com.biblioteca.bibliotecaddd.loans.domain.model.valueobjects;

public record DiasRetraso(long value) {
    public DiasRetraso {
        if (value < 0) throw new IllegalArgumentException("Los días de retraso no pueden ser negativos");
    }
}