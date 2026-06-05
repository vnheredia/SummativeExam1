package com.biblioteca.bibliotecaddd.users.domain.model;

import com.biblioteca.bibliotecaddd.users.domain.model.valueobjects.NombreUsuario;
import com.biblioteca.bibliotecaddd.users.domain.model.valueobjects.UserId;

public class Usuario {
    private final UserId id;
    private NombreUsuario nombre;

    public Usuario(UserId id, NombreUsuario nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    // Getters públicos (solo lectura)
    public UserId getId() { return id; }
    public NombreUsuario getNombre() { return nombre; }

    // Comportamiento: cambiar nombre
    public void cambiarNombre(NombreUsuario nuevoNombre) {
        this.nombre = nuevoNombre;
    }
}