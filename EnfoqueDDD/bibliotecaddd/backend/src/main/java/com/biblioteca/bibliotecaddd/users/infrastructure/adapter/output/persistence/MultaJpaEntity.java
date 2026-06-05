package com.biblioteca.bibliotecaddd.users.infrastructure.adapter.output.persistence;

import jakarta.persistence.*;

@Entity
@Table(name = "multas")
public class MultaJpaEntity {
    @Id
    private String usuarioId;
    private double montoPendiente;

    public MultaJpaEntity() {}
    public MultaJpaEntity(String usuarioId, double montoPendiente) { this.usuarioId = usuarioId; this.montoPendiente = montoPendiente; }
    public String getUsuarioId() { return usuarioId; }
    public void setUsuarioId(String usuarioId) { this.usuarioId = usuarioId; }
    public double getMontoPendiente() { return montoPendiente; }
    public void setMontoPendiente(double montoPendiente) { this.montoPendiente = montoPendiente; }
}