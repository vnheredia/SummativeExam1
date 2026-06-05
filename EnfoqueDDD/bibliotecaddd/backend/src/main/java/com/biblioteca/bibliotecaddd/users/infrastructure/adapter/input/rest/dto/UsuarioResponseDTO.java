package com.biblioteca.bibliotecaddd.users.infrastructure.adapter.input.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UsuarioResponseDTO {
    private String id;
    private String nombre;
}