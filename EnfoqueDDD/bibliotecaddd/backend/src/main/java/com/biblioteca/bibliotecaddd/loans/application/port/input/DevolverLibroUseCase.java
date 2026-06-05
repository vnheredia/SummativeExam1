package com.biblioteca.bibliotecaddd.loans.application.port.input;

import com.biblioteca.bibliotecaddd.loans.application.dto.DevolverLibroRequest;
import com.biblioteca.bibliotecaddd.loans.application.dto.PrestamoResponse;

public interface DevolverLibroUseCase {
    PrestamoResponse devolver(DevolverLibroRequest request);
}