package com.biblioteca.bibliotecaddd.loans.domain.model.valueobjects;

public record MontoMulta(double value) {
    public MontoMulta {
        if (value < 0) throw new IllegalArgumentException("El monto de multa no puede ser negativo");
    }

    public MontoMulta sumar(MontoMulta otro) {
        return new MontoMulta(this.value + otro.value);
    }
}