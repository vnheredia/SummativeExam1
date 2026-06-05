package com.biblioteca.bibliotecaddd.loans.application.dto;

import java.time.LocalDate;

public class ReservaResponse {
    private String id;
    private String usuarioId;
    private String libroCodigo;
    private String estado;
    private LocalDate fechaReserva;

    public ReservaResponse() {}
    public ReservaResponse(String id, String usuarioId, String libroCodigo, String estado, LocalDate fechaReserva) {
        this.id = id;
        this.usuarioId = usuarioId;
        this.libroCodigo = libroCodigo;
        this.estado = estado;
        this.fechaReserva = fechaReserva;
    }
    // Getters y setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getUsuarioId() { return usuarioId; }
    public void setUsuarioId(String usuarioId) { this.usuarioId = usuarioId; }
    public String getLibroCodigo() { return libroCodigo; }
    public void setLibroCodigo(String libroCodigo) { this.libroCodigo = libroCodigo; }
    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
    public LocalDate getFechaReserva() { return fechaReserva; }
    public void setFechaReserva(LocalDate fechaReserva) { this.fechaReserva = fechaReserva; }
}