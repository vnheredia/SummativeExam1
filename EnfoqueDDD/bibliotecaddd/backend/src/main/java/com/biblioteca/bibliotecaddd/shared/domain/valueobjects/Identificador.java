package com.biblioteca.bibliotecaddd.shared.domain.valueobjects;

import java.util.Objects;
import java.util.regex.Pattern;

/**
 * Value Object base para identificadores alfanuméricos con formato.
 * Puede ser extendido o usado directamente.
 */
public class Identificador {
    private final String valor;
    private static final Pattern PATRON = Pattern.compile("^[A-Za-z0-9\\-]{3,20}$");

    public Identificador(String valor) {
        if (valor == null || !PATRON.matcher(valor).matches()) {
            throw new IllegalArgumentException("Identificador inválido. Use 3-20 caracteres alfanuméricos o guiones.");
        }
        this.valor = valor;
    }

    public String getValor() { return valor; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Identificador that = (Identificador) o;
        return Objects.equals(valor, that.valor);
    }

    @Override
    public int hashCode() {
        return Objects.hash(valor);
    }

    @Override
    public String toString() {
        return valor;
    }
}