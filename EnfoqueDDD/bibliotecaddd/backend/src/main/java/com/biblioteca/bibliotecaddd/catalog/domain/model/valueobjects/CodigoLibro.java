package com.biblioteca.bibliotecaddd.catalog.domain.model.valueobjects;

import jakarta.persistence.Embeddable;
import java.util.Objects;
import java.util.regex.Pattern;

@Embeddable
public class CodigoLibro {
    private String value;

    private static final Pattern PATTERN = Pattern.compile("^[A-Z0-9]{3,10}$");

    protected CodigoLibro() {}

    public CodigoLibro(String value) {
        if (value == null || !PATTERN.matcher(value).matches()) {
            throw new IllegalArgumentException("Código de libro inválido. Debe tener 3-10 caracteres alfanuméricos en mayúsculas.");
        }
        this.value = value;
    }

    public String getValue() { return value; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CodigoLibro that = (CodigoLibro) o;
        return Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}