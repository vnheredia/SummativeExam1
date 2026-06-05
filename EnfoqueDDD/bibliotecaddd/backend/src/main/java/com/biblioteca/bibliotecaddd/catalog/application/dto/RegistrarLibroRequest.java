package com.biblioteca.bibliotecaddd.catalog.application.dto;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegistrarLibroRequest {
    @NotBlank
    @Pattern(regexp = "^[A-Z0-9]{3,10}$", message = "Código inválido")
    private String codigo;

    @NotBlank
    private String titulo;

    @NotBlank
    private String autor;

    @Min(1)
    private int stock;
}