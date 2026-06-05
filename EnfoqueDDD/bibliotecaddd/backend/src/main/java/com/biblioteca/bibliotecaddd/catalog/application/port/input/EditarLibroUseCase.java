package com.biblioteca.bibliotecaddd.catalog.application.port.input;

import com.biblioteca.bibliotecaddd.catalog.application.dto.EditarLibroRequest;
import com.biblioteca.bibliotecaddd.catalog.application.dto.LibroResponse;

public interface EditarLibroUseCase {
    LibroResponse editar(String codigo, EditarLibroRequest request);
}
