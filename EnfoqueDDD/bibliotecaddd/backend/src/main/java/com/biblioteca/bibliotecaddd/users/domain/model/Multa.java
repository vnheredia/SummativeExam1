package com.biblioteca.bibliotecaddd.users.domain.model;

import com.biblioteca.bibliotecaddd.users.domain.model.valueobjects.UserId;
import java.util.Objects;

public class Multa {
    private final UserId usuarioId;
    private double montoPendiente;

    public Multa(UserId usuarioId, double montoInicial) {
        this.usuarioId = usuarioId;
        this.montoPendiente = montoInicial;
    }

    public UserId getUsuarioId() { return usuarioId; }
    public double getMontoPendiente() { return montoPendiente; }

    public void agregarMonto(double monto) {
        if (monto <= 0) throw new IllegalArgumentException("El monto debe ser positivo");
        this.montoPendiente += monto;
    }

    public double pagar() {
        double pagado = this.montoPendiente;
        this.montoPendiente = 0;
        return pagado;
    }

    public boolean tieneMultas() {
        return montoPendiente > 0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Multa multa = (Multa) o;
        return Objects.equals(usuarioId, multa.usuarioId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(usuarioId);
    }
}