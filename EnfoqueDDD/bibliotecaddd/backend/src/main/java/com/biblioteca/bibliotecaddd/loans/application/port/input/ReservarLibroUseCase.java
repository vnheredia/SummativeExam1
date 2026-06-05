package com.biblioteca.bibliotecaddd.loans.application.port.input;

import com.biblioteca.bibliotecaddd.loans.application.dto.ReservarLibroRequest;
import com.biblioteca.bibliotecaddd.loans.application.dto.ReservaResponse;

public interface ReservarLibroUseCase {
    ReservaResponse reservar(ReservarLibroRequest request);
}