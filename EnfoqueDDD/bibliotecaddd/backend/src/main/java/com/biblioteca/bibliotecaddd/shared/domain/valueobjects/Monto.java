package com.biblioteca.bibliotecaddd.shared.domain.valueobjects;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

/**
 * Value Object para montos monetarios con dos decimales.
 */
public class Monto {
    private final BigDecimal valor;

    public Monto(double valor) {
        this(BigDecimal.valueOf(valor));
    }

    public Monto(BigDecimal valor) {
        if (valor == null) throw new IllegalArgumentException("El monto no puede ser nulo");
        if (valor.compareTo(BigDecimal.ZERO) < 0) throw new IllegalArgumentException("El monto no puede ser negativo");
        this.valor = valor.setScale(2, RoundingMode.HALF_EVEN);
    }

    public BigDecimal getValor() { return valor; }

    public Monto sumar(Monto otro) {
        return new Monto(this.valor.add(otro.valor));
    }

    public Monto restar(Monto otro) {
        return new Monto(this.valor.subtract(otro.valor));
    }

    public boolean esCero() {
        return this.valor.compareTo(BigDecimal.ZERO) == 0;
    }

    public boolean esMayorQue(Monto otro) {
        return this.valor.compareTo(otro.valor) > 0;
    }

    public double toDouble() {
        return valor.doubleValue();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Monto monto = (Monto) o;
        return Objects.equals(valor, monto.valor);
    }

    @Override
    public int hashCode() {
        return Objects.hash(valor);
    }

    @Override
    public String toString() {
        return valor.toPlainString() + " €";
    }
}