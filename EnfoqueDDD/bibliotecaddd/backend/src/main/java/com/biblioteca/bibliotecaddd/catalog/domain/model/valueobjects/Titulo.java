package com.biblioteca.bibliotecaddd.catalog.domain.model.valueobjects;

import jakarta.persistence.Embeddable;
import java.util.Objects;

@Embeddable
public class Titulo {
    private String value;

    protected Titulo() {}

    public Titulo(String value) {
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException("El título no puede estar vacío");
        }
        this.value = value.trim();
    }

    public String getValue() { return value; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Titulo titulo = (Titulo) o;
        return Objects.equals(value, titulo.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}