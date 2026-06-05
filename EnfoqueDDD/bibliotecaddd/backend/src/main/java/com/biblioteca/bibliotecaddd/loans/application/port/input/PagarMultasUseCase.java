
package com.biblioteca.bibliotecaddd.loans.application.port.input;

import com.biblioteca.bibliotecaddd.loans.application.dto.MultaResponse;
import com.biblioteca.bibliotecaddd.loans.application.dto.PagarMultasRequest;

public interface PagarMultasUseCase {
    MultaResponse pagar(PagarMultasRequest request);
}