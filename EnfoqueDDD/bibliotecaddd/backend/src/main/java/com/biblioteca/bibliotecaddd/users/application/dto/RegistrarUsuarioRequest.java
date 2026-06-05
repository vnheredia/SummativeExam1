package com.biblioteca.bibliotecaddd.users.application.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegistrarUsuarioRequest {
    @NotBlank
    @Pattern(regexp = "^[a-zA-Z0-9\\-]{5,20}$", message = "ID inválido")
    private String id;

    @NotBlank
    private String nombre;
}
