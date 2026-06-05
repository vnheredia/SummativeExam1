package com.biblioteca.bibliotecaddd.loans.domain.service;

import com.biblioteca.bibliotecaddd.loans.application.port.output.PrestamoRepositoryPort;
import org.springframework.stereotype.Service;

@Service
public class PrestamoDomainService {
    private final PrestamoRepositoryPort prestamoRepository;

    public PrestamoDomainService(PrestamoRepositoryPort prestamoRepository) {
        this.prestamoRepository = prestamoRepository;
    }

    public boolean puedeSolicitarPrestamo(String usuarioId) {
        long activos = prestamoRepository.findActivosByUsuarioId(usuarioId).size();
        return activos < 3;
    }

    public boolean yaTienePrestamoActivoDelLibro(String usuarioId, String libroCodigo) {
        return prestamoRepository.existsActivoByUsuarioAndLibro(usuarioId, libroCodigo);
    }
}