package com.biblioteca.bibliotecaddd.catalog.domain.model.valueobjects;

import jakarta.persistence.Embeddable;
import java.util.Objects;

@Embeddable
public class Autor {
    private String value;

    protected Autor() {}

    public Autor(String value) {
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException("El autor no puede estar vacío");
        }
        this.value = value.trim();
    }

    public String getValue() { return value; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Autor autor = (Autor) o;
        return Objects.equals(value, autor.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}