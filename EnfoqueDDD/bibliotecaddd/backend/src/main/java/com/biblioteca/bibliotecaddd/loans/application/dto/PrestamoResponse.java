package com.biblioteca.bibliotecaddd.loans.application.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PrestamoResponse {
    private String id;
    private String usuarioId;
    private String libroCodigo;
    private String estado;
    private LocalDate fechaPrestamo;
    private LocalDate fechaLimite;
    private Double multaGenerada;
}