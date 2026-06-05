package com.biblioteca.bibliotecaddd.shared.domain.valueobjects;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

/**
 * Value Object para fechas con formato estándar.
 * Encapsula LocalDate y ofrece métodos útiles.
 */
public class Fecha {
    private final LocalDate valor;
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public Fecha(LocalDate valor) {
        if (valor == null) throw new IllegalArgumentException("La fecha no puede ser nula");
        this.valor = valor;
    }

    public LocalDate getValor() { return valor; }

    public boolean esPosteriorA(Fecha otra) {
        return this.valor.isAfter(otra.valor);
    }

    public boolean esAnteriorA(Fecha otra) {
        return this.valor.isBefore(otra.valor);
    }

    public long diasHasta(Fecha otra) {
        return java.time.temporal.ChronoUnit.DAYS.between(this.valor, otra.valor);
    }

    public String formatear() {
        return valor.format(FORMATTER);
    }

    public static Fecha hoy() {
        return new Fecha(LocalDate.now());
    }

    public Fecha masDias(int dias) {
        return new Fecha(this.valor.plusDays(dias));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Fecha fecha = (Fecha) o;
        return Objects.equals(valor, fecha.valor);
    }

    @Override
    public int hashCode() {
        return Objects.hash(valor);
    }

    @Override
    public String toString() {
        return formatear();
    }
}