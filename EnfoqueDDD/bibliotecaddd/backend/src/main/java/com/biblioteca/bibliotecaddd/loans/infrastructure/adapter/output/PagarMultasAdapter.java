package com.biblioteca.bibliotecaddd.loans.infrastructure.adapter.output;

import com.biblioteca.bibliotecaddd.users.application.port.output.PagarMultasPort;
import org.springframework.stereotype.Component;

@Component
public class PagarMultasAdapter implements PagarMultasPort {
    private final PagarMultasPort pagarMultasPort;
    public PagarMultasAdapter(PagarMultasPort pagarMultasPort) {
        this.pagarMultasPort = pagarMultasPort;
    }
    @Override
    public double pagarMultas(String userId) {
        return pagarMultasPort.pagarMultas(userId);
    }
}