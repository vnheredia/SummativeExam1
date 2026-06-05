package com.biblioteca.bibliotecaddd.users.domain.model.valueobjects;

import jakarta.persistence.Embeddable;
import java.util.Objects;
import java.util.regex.Pattern;

@Embeddable
public class UserId {
    private String value;

    // Patrón: alfanumérico, guiones, longitud 5-20
    private static final Pattern VALID_PATTERN = Pattern.compile("^[a-zA-Z0-9\\-]{5,20}$");

    protected UserId() {} // JPA

    public UserId(String value) {
        if (value == null || !VALID_PATTERN.matcher(value).matches()) {
            throw new IllegalArgumentException("UserId inválido. Debe tener 5-20 caracteres alfanuméricos o guiones.");
        }
        this.value = value;
    }

    public String getValue() { return value; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserId userId = (UserId) o;
        return Objects.equals(value, userId.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}