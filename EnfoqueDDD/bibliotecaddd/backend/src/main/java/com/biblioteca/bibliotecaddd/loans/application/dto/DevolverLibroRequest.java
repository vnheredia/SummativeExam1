package com.biblioteca.bibliotecaddd.loans.application.dto;

import jakarta.validation.constraints.NotBlank;

public class DevolverLibroRequest {
    @NotBlank private String prestamoId;

    public DevolverLibroRequest() {}
    public DevolverLibroRequest(String prestamoId) { this.prestamoId = prestamoId; }
    public String getPrestamoId() { return prestamoId; }
    public void setPrestamoId(String prestamoId) { this.prestamoId = prestamoId; }
}