package com.biblioteca.bibliotecaddd.users.application.service;

import com.biblioteca.bibliotecaddd.users.application.port.input.GestionarMultasUseCase;
import com.biblioteca.bibliotecaddd.users.application.port.output.ConsultarMultasPort;
import com.biblioteca.bibliotecaddd.users.application.port.output.PagarMultasPort;
import com.biblioteca.bibliotecaddd.users.application.port.output.RegistrarMultaPort;
import com.biblioteca.bibliotecaddd.users.domain.model.valueobjects.UserId;
import com.biblioteca.bibliotecaddd.users.domain.service.MultaDomainService;
import org.springframework.stereotype.Service;

@Service
public class MultaApplicationService implements GestionarMultasUseCase, ConsultarMultasPort, RegistrarMultaPort, PagarMultasPort {

    private final MultaDomainService multaDomainService;

    public MultaApplicationService(MultaDomainService multaDomainService) {
        this.multaDomainService = multaDomainService;
    }

    @Override
    public boolean tieneMultasPendientes(String userId) {
        return multaDomainService.tieneMultasPendientes(new UserId(userId));
    }

    @Override
    public void registrarMulta(String userId, double monto) {
        multaDomainService.agregarMulta(new UserId(userId), monto);
    }

    @Override
    public double pagarMultas(String userId) {
        return multaDomainService.pagarMultas(new UserId(userId));
    }
}