package com.biblioteca.bibliotecaddd.users.application.port.output;

public interface ConsultarUsuarioPort {
    boolean existsById(String userId);
}