package com.biblioteca.bibliotecaddd.loans.application.dto;

import jakarta.validation.constraints.NotBlank;

public class CancelarReservaRequest {
    @NotBlank private String reservaId;

    public CancelarReservaRequest() {}
    public CancelarReservaRequest(String reservaId) { this.reservaId = reservaId; }
    public String getReservaId() { return reservaId; }
    public void setReservaId(String reservaId) { this.reservaId = reservaId; }
}