package com.biblioteca.bibliotecaddd.loans.domain.model;

import com.biblioteca.bibliotecaddd.loans.domain.model.valueobjects.IdPrestamo;
import com.biblioteca.bibliotecaddd.loans.domain.model.valueobjects.MontoMulta;
import com.biblioteca.bibliotecaddd.loans.domain.model.valueobjects.PeriodoPrestamo;
import com.biblioteca.bibliotecaddd.loans.domain.events.LibroDevueltoEvent;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class Prestamo {
    private final IdPrestamo id;
    private final String usuarioId;
    private final String libroCodigo;
    private EstadoPrestamo estado;
    private final PeriodoPrestamo periodo;
    private MontoMulta multaGenerada; // solo si se devuelve tarde

    public Prestamo(IdPrestamo id, String usuarioId, String libroCodigo, PeriodoPrestamo periodo) {
        this.id = id;
        this.usuarioId = usuarioId;
        this.libroCodigo = libroCodigo;
        this.estado = EstadoPrestamo.ACTIVO;
        this.periodo = periodo;
        this.multaGenerada = new MontoMulta(0);
    }

    // Comportamiento principal: devolver
    public LibroDevueltoEvent devolver(LocalDate fechaDevolucion) {
        if (this.estado == EstadoPrestamo.DEVUELTO) {
            throw new IllegalStateException("El préstamo ya fue devuelto");
        }
        long diasRetraso = periodo.calcularDiasRetraso(fechaDevolucion);
        this.estado = EstadoPrestamo.DEVUELTO;
        if (diasRetraso > 0) {
            this.multaGenerada = new MontoMulta(diasRetraso * 1.0); // 1€ por día
            return new LibroDevueltoEvent(this.id.getValue(), this.usuarioId, this.libroCodigo, this.multaGenerada);
        }
        return new LibroDevueltoEvent(this.id.getValue(), this.usuarioId, this.libroCodigo, null);
    }

    public boolean estaActivo() {
        return estado == EstadoPrestamo.ACTIVO;
    }
}