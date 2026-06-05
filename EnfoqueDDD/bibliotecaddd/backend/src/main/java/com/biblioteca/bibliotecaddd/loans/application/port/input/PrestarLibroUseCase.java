package com.biblioteca.bibliotecaddd.loans.application.port.input;

import com.biblioteca.bibliotecaddd.loans.application.dto.PrestarLibroRequest;
import com.biblioteca.bibliotecaddd.loans.application.dto.PrestamoResponse;

public interface PrestarLibroUseCase {
    PrestamoResponse prestar(PrestarLibroRequest request);
}