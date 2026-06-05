package com.biblioteca.bibliotecaddd.loans.domain.model.valueobjects;

import jakarta.persistence.Embeddable;
import java.util.Objects;
import java.util.regex.Pattern;

@Embeddable
public class IdReserva {
    private String value;
    private static final Pattern PATTERN = Pattern.compile("^R[0-9]+$");

    protected IdReserva() {}

    public IdReserva(String value) {
        if (value == null || !PATTERN.matcher(value).matches()) {
            throw new IllegalArgumentException("ID de reserva inválido. Formato: R seguido de números");
        }
        this.value = value;
    }

    public String getValue() { return value; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        IdReserva that = (IdReserva) o;
        return Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}