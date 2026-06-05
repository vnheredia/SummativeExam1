package com.biblioteca.bibliotecaddd.loans.application.dto;

import java.time.LocalDate;

public class PrestamoResponse {
    private String id;
    private String usuarioId;
    private String libroCodigo;
    private String estado;
    private LocalDate fechaPrestamo;
    private LocalDate fechaLimite;
    private Double multaGenerada;

    public PrestamoResponse() {}
    public PrestamoResponse(String id, String usuarioId, String libroCodigo, String estado,
                            LocalDate fechaPrestamo, LocalDate fechaLimite, Double multaGenerada) {
        this.id = id;
        this.usuarioId = usuarioId;
        this.libroCodigo = libroCodigo;
        this.estado = estado;
        this.fechaPrestamo = fechaPrestamo;
        this.fechaLimite = fechaLimite;
        this.multaGenerada = multaGenerada;
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
    public LocalDate getFechaPrestamo() { return fechaPrestamo; }
    public void setFechaPrestamo(LocalDate fechaPrestamo) { this.fechaPrestamo = fechaPrestamo; }
    public LocalDate getFechaLimite() { return fechaLimite; }
    public void setFechaLimite(LocalDate fechaLimite) { this.fechaLimite = fechaLimite; }
    public Double getMultaGenerada() { return multaGenerada; }
    public void setMultaGenerada(Double multaGenerada) { this.multaGenerada = multaGenerada; }
}