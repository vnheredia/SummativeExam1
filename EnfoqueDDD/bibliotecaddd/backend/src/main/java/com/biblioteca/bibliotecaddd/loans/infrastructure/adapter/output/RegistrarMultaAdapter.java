package com.biblioteca.bibliotecaddd.loans.infrastructure.adapter.output;

import com.biblioteca.bibliotecaddd.users.application.port.output.RegistrarMultaPort;
import org.springframework.stereotype.Component;

@Component
public class RegistrarMultaAdapter implements RegistrarMultaPort {
    private final RegistrarMultaPort registrarMultaPort;
    public RegistrarMultaAdapter(RegistrarMultaPort registrarMultaPort) {
        this.registrarMultaPort = registrarMultaPort;
    }
    @Override
    public void registrarMulta(String userId, double monto) {
        registrarMultaPort.registrarMulta(userId, monto);
    }
}