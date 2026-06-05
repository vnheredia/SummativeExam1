package com.biblioteca.bibliotecaddd.loans.application.dto;

import jakarta.validation.constraints.NotBlank;

public class PagarMultasRequest {
    @NotBlank private String usuarioId;

    public PagarMultasRequest() {}
    public PagarMultasRequest(String usuarioId) { this.usuarioId = usuarioId; }
    public String getUsuarioId() { return usuarioId; }
    public void setUsuarioId(String usuarioId) { this.usuarioId = usuarioId; }
}