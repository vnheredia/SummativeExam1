package com.biblioteca.bibliotecaddd.loans.domain.model;

import com.biblioteca.bibliotecaddd.loans.domain.model.valueobjects.IdReserva;
import java.time.LocalDate;

public class Reserva {
    private final IdReserva id;
    private final String usuarioId;
    private final String libroCodigo;
    private final LocalDate fechaReserva;
    private EstadoReserva estado;

    public Reserva(IdReserva id, String usuarioId, String libroCodigo, LocalDate fechaReserva) {
        this.id = id;
        this.usuarioId = usuarioId;
        this.libroCodigo = libroCodigo;
        this.fechaReserva = fechaReserva;
        this.estado = EstadoReserva.ACTIVA;
    }

    public void cancelar() {
        if (this.estado != EstadoReserva.ACTIVA) {
            throw new IllegalStateException("Solo se puede cancelar una reserva activa");
        }
        this.estado = EstadoReserva.CANCELADA;
    }

    public void completar() {
        if (this.estado != EstadoReserva.ACTIVA) {
            throw new IllegalStateException("Solo se puede completar una reserva activa");
        }
        this.estado = EstadoReserva.COMPLETADA;
    }

    public boolean esActiva() {
        return estado == EstadoReserva.ACTIVA;
    }

    // Getters
    public IdReserva getId() { return id; }
    public String getUsuarioId() { return usuarioId; }
    public String getLibroCodigo() { return libroCodigo; }
    public LocalDate getFechaReserva() { return fechaReserva; }
    public EstadoReserva getEstado() { return estado; }

    // Setter público para reconstrucción (solo para el adaptador)
    public void setEstado(EstadoReserva estado) {
        this.estado = estado;
    }
}