package com.biblioteca.bibliotecaddd.loans.infrastructure.adapter.output.persistence;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "prestamos")
public class PrestamoJpaEntity {
    @Id
    private String id;
    private String usuarioId;
    private String libroCodigo;
    private String estado;
    private LocalDate fechaInicio;
    private LocalDate fechaLimite;
    private Double multaGenerada;

    public PrestamoJpaEntity() {}
    public PrestamoJpaEntity(String id, String usuarioId, String libroCodigo, String estado, LocalDate fechaInicio, LocalDate fechaLimite, Double multaGenerada) {
        this.id = id; this.usuarioId = usuarioId; this.libroCodigo = libroCodigo; this.estado = estado; this.fechaInicio = fechaInicio; this.fechaLimite = fechaLimite; this.multaGenerada = multaGenerada;
    }
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getUsuarioId() { return usuarioId; }
    public void setUsuarioId(String usuarioId) { this.usuarioId = usuarioId; }
    public String getLibroCodigo() { return libroCodigo; }
    public void setLibroCodigo(String libroCodigo) { this.libroCodigo = libroCodigo; }
    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
    public LocalDate getFechaInicio() { return fechaInicio; }
    public void setFechaInicio(LocalDate fechaInicio) { this.fechaInicio = fechaInicio; }
    public LocalDate getFechaLimite() { return fechaLimite; }
    public void setFechaLimite(LocalDate fechaLimite) { this.fechaLimite = fechaLimite; }
    public Double getMultaGenerada() { return multaGenerada; }
    public void setMultaGenerada(Double multaGenerada) { this.multaGenerada = multaGenerada; }
}