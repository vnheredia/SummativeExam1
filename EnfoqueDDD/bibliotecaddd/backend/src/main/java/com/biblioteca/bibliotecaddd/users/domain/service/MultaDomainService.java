package com.biblioteca.bibliotecaddd.users.domain.service;

import com.biblioteca.bibliotecaddd.users.application.port.output.MultaRepositoryPort;
import com.biblioteca.bibliotecaddd.users.domain.model.Multa;
import com.biblioteca.bibliotecaddd.users.domain.model.valueobjects.UserId;
import org.springframework.stereotype.Service;

@Service
public class MultaDomainService {

    private final MultaRepositoryPort multaRepository;

    public MultaDomainService(MultaRepositoryPort multaRepository) {
        this.multaRepository = multaRepository;
    }

    public boolean tieneMultasPendientes(UserId userId) {
        return multaRepository.findByUsuarioId(userId)
                .map(Multa::tieneMultas)
                .orElse(false);
    }

    public void agregarMulta(UserId userId, double monto) {
        Multa multa = multaRepository.findByUsuarioId(userId)
                .orElse(new Multa(userId, 0));
        multa.agregarMonto(monto);
        multaRepository.save(multa);
    }

    public double pagarMultas(UserId userId) {
        Multa multa = multaRepository.findByUsuarioId(userId)
                .orElseThrow(() -> new IllegalArgumentException("El usuario no tiene multas registradas"));
        double pagado = multa.pagar();
        multaRepository.save(multa);
        return pagado;
    }
}