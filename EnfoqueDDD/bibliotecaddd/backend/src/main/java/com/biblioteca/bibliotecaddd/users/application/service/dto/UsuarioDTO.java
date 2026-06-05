package com.biblioteca.bibliotecaddd.users.application.service.dto;

public class UsuarioDTO {
    private final String id;
    private final String nombre;
    
    public UsuarioDTO(String id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }
    
    public String getId() { return id; }
    public String getNombre() { return nombre; }
}