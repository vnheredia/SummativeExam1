package com.biblioteca.bibliotecaddd.loans.domain.model;

import com.biblioteca.bibliotecaddd.loans.domain.events.LibroDevueltoEvent;
import com.biblioteca.bibliotecaddd.loans.domain.model.valueobjects.IdPrestamo;
import com.biblioteca.bibliotecaddd.loans.domain.model.valueobjects.MontoMulta;
import com.biblioteca.bibliotecaddd.loans.domain.model.valueobjects.PeriodoPrestamo;
import java.time.LocalDate;

public class Prestamo {
    private final IdPrestamo id;
    private final String usuarioId;
    private final String libroCodigo;
    private EstadoPrestamo estado;
    private final PeriodoPrestamo periodo;
    private MontoMulta multaGenerada;

    public Prestamo(IdPrestamo id, String usuarioId, String libroCodigo, PeriodoPrestamo periodo) {
        this.id = id;
        this.usuarioId = usuarioId;
        this.libroCodigo = libroCodigo;
        this.estado = EstadoPrestamo.ACTIVO;
        this.periodo = periodo;
        this.multaGenerada = new MontoMulta(0);
    }

    public LibroDevueltoEvent devolver(LocalDate fechaDevolucion) {
        if (this.estado == EstadoPrestamo.DEVUELTO) {
            throw new IllegalStateException("El préstamo ya fue devuelto");
        }
        long diasRetraso = periodo.calcularDiasRetraso(fechaDevolucion);
        this.estado = EstadoPrestamo.DEVUELTO;
        if (diasRetraso > 0) {
            this.multaGenerada = new MontoMulta(diasRetraso * 1.0);
            return new LibroDevueltoEvent(this.id.getValue(), this.usuarioId, this.libroCodigo, this.multaGenerada);
        }
        return new LibroDevueltoEvent(this.id.getValue(), this.usuarioId, this.libroCodigo, null);
    }

    public boolean estaActivo() {
        return estado == EstadoPrestamo.ACTIVO;
    }

    // Getters
    public IdPrestamo getId() { return id; }
    public String getUsuarioId() { return usuarioId; }
    public String getLibroCodigo() { return libroCodigo; }
    public EstadoPrestamo getEstado() { return estado; }
    public PeriodoPrestamo getPeriodo() { return periodo; }
    public MontoMulta getMultaGenerada() { return multaGenerada; }

    // Setters públicos para reconstrucción (solo para el adaptador)
    public void setEstado(EstadoPrestamo estado) {
        this.estado = estado;
    }

    public void setMultaGenerada(MontoMulta multaGenerada) {
        this.multaGenerada = multaGenerada;
    }
}