package com.biblioteca.bibliotecaddd.users.application.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public class RegistrarUsuarioRequest {
    @NotBlank
    @Pattern(regexp = "^[a-zA-Z0-9\\-]{5,20}$", message = "ID inválido")
    private String id;

    @NotBlank
    private String nombre;

    public RegistrarUsuarioRequest() {}
    public RegistrarUsuarioRequest(String id, String nombre) { this.id = id; this.nombre = nombre; }
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
}