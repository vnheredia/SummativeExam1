package com.biblioteca.bibliotecaddd.catalog.application.port.input;

import com.biblioteca.bibliotecaddd.catalog.application.dto.RegistrarLibroRequest;
import com.biblioteca.bibliotecaddd.catalog.application.dto.LibroResponse;

public interface RegistrarLibroUseCase {
    LibroResponse registrar(RegistrarLibroRequest request);
}