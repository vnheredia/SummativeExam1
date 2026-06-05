package com.biblioteca.bibliotecaddd.loans.application.dto;

import jakarta.validation.constraints.NotBlank;

public class PrestarLibroRequest {
    @NotBlank private String usuarioId;
    @NotBlank private String libroCodigo;

    public PrestarLibroRequest() {}
    public PrestarLibroRequest(String usuarioId, String libroCodigo) {
        this.usuarioId = usuarioId;
        this.libroCodigo = libroCodigo;
    }
    public String getUsuarioId() { return usuarioId; }
    public void setUsuarioId(String usuarioId) { this.usuarioId = usuarioId; }
    public String getLibroCodigo() { return libroCodigo; }
    public void setLibroCodigo(String libroCodigo) { this.libroCodigo = libroCodigo; }
}