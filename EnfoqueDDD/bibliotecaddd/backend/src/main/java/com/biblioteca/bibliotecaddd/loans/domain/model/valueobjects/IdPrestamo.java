package com.biblioteca.bibliotecaddd.loans.domain.model.valueobjects;

import jakarta.persistence.Embeddable;
import java.util.Objects;
import java.util.regex.Pattern;

@Embeddable
public class IdPrestamo {
    private String value;
    private static final Pattern PATTERN = Pattern.compile("^P[0-9]+$");

    protected IdPrestamo() {}

    public IdPrestamo(String value) {
        if (value == null || !PATTERN.matcher(value).matches()) {
            throw new IllegalArgumentException("ID de préstamo inválido. Formato: P seguido de números");
        }
        this.value = value;
    }

    public String getValue() { return value; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        IdPrestamo that = (IdPrestamo) o;
        return Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}