package com.biblioteca.bibliotecaddd.loans.application.dto;

public class MultaResponse {
    private String usuarioId;
    private double montoPendiente;

    public MultaResponse() {}
    public MultaResponse(String usuarioId, double montoPendiente) {
        this.usuarioId = usuarioId;
        this.montoPendiente = montoPendiente;
    }
    public String getUsuarioId() { return usuarioId; }
    public void setUsuarioId(String usuarioId) { this.usuarioId = usuarioId; }
    public double getMontoPendiente() { return montoPendiente; }
    public void setMontoPendiente(double montoPendiente) { this.montoPendiente = montoPendiente; }
}