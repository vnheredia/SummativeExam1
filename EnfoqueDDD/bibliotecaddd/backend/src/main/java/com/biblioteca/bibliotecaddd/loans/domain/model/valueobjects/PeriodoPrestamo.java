package com.biblioteca.bibliotecaddd.loans.domain.model.valueobjects;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class PeriodoPrestamo {
    private final LocalDate fechaInicio;
    private final LocalDate fechaLimite;

    public PeriodoPrestamo(LocalDate fechaInicio, int diasPlazo) {
        if (fechaInicio == null) throw new IllegalArgumentException("La fecha de inicio es requerida");
        if (diasPlazo <= 0) throw new IllegalArgumentException("El plazo debe ser positivo");
        this.fechaInicio = fechaInicio;
        this.fechaLimite = fechaInicio.plusDays(diasPlazo);
    }

    public LocalDate getFechaInicio() { return fechaInicio; }
    public LocalDate getFechaLimite() { return fechaLimite; }

    public long calcularDiasRetraso(LocalDate fechaDevolucion) {
        if (fechaDevolucion == null) return 0;
        if (fechaDevolucion.isBefore(fechaLimite)) return 0;
        return ChronoUnit.DAYS.between(fechaLimite, fechaDevolucion);
    }
}