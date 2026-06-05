package com.biblioteca.bibliotecaddd.catalog.application.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EditarLibroRequest {
    @NotBlank
    private String titulo;

    @NotBlank
    private String autor;

    @Min(0)
    private int stock;
}