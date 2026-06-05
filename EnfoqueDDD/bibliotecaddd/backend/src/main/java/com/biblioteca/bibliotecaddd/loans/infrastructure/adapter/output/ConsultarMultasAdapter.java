package com.biblioteca.bibliotecaddd.loans.infrastructure.adapter.output;

import com.biblioteca.bibliotecaddd.users.application.port.output.ConsultarMultasPort;
import org.springframework.stereotype.Component;

@Component
public class ConsultarMultasAdapter implements ConsultarMultasPort {
    private final ConsultarMultasPort consultarMultasPort; // inyectamos la implementación real desde users
    public ConsultarMultasAdapter(ConsultarMultasPort consultarMultasPort) {
        this.consultarMultasPort = consultarMultasPort;
    }
    @Override
    public boolean tieneMultasPendientes(String userId) {
        return consultarMultasPort.tieneMultasPendientes(userId);
    }
}