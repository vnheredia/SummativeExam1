package com.biblioteca.bibliotecaddd.users.application.dto;

import jakarta.validation.constraints.NotBlank;

public class EditarUsuarioRequest {
    @NotBlank private String nombre;
    public EditarUsuarioRequest() {}
    public EditarUsuarioRequest(String nombre) { this.nombre = nombre; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
}