package com.biblioteca.bibliotecaddd.loans.application.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MultaResponse {
    private String usuarioId;
    private double montoPendiente;
}