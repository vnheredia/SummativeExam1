package com.biblioteca.bibliotecaddd.catalog.domain.model.valueobjects;

import jakarta.persistence.Embeddable;
import java.util.Objects;

@Embeddable
public class Stock {
    private int value;

    protected Stock() {}

    public Stock(int value) {
        if (value < 0) {
            throw new IllegalArgumentException("El stock no puede ser negativo");
        }
        this.value = value;
    }

    public int getValue() { return value; }

    public Stock incrementar(int cantidad) {
        if (cantidad <= 0) throw new IllegalArgumentException("Cantidad a incrementar debe ser positiva");
        return new Stock(this.value + cantidad);
    }

    public Stock decrementar(int cantidad) {
        if (cantidad <= 0) throw new IllegalArgumentException("Cantidad a decrementar debe ser positiva");
        int nuevo = this.value - cantidad;
        if (nuevo < 0) throw new IllegalStateException("Stock insuficiente");
        return new Stock(nuevo);
    }

    public boolean isDisponible() {
        return value > 0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Stock stock = (Stock) o;
        return value == stock.value;
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}