package com.biblioteca.bibliotecaddd.loans.application.service;

import com.biblioteca.bibliotecaddd.catalog.application.port.output.ActualizarStockPort;
import com.biblioteca.bibliotecaddd.catalog.application.port.output.ConsultarStockPort;
import com.biblioteca.bibliotecaddd.loans.application.dto.*;
import com.biblioteca.bibliotecaddd.loans.application.port.input.*;
import com.biblioteca.bibliotecaddd.loans.application.port.output.PrestamoRepositoryPort;
import com.biblioteca.bibliotecaddd.loans.application.port.output.ReservaRepositoryPort;
import com.biblioteca.bibliotecaddd.loans.domain.events.LibroDevueltoEvent;
import com.biblioteca.bibliotecaddd.loans.domain.model.*;
import com.biblioteca.bibliotecaddd.loans.domain.model.valueobjects.*;
import com.biblioteca.bibliotecaddd.loans.domain.service.PrestamoDomainService;
import com.biblioteca.bibliotecaddd.users.application.port.output.ConsultarMultasPort;
import com.biblioteca.bibliotecaddd.users.application.port.output.ConsultarUsuarioPort;
import com.biblioteca.bibliotecaddd.users.application.port.output.PagarMultasPort;
import com.biblioteca.bibliotecaddd.users.application.port.output.RegistrarMultaPort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class PrestamoApplicationService implements
        PrestarLibroUseCase,
        DevolverLibroUseCase,          
        ReservarLibroUseCase,
        CancelarReservaUseCase,
        MostrarPrestamosUseCase,
        MostrarReservasUseCase,
        PagarMultasUseCase {

    private final PrestamoRepositoryPort prestamoRepository;
    private final ReservaRepositoryPort reservaRepository;
    private final PrestamoDomainService domainService;
    private final ConsultarUsuarioPort consultarUsuarioPort;
    private final ConsultarMultasPort consultarMultasPort;
    private final ConsultarStockPort consultarStockPort;
    private final ActualizarStockPort actualizarStockPort;
    private final RegistrarMultaPort registrarMultaPort;
    private final PagarMultasPort pagarMultasPort;

    private int contadorPrestamos = 1;
    private int contadorReservas = 1;

    public PrestamoApplicationService(PrestamoRepositoryPort prestamoRepository,
                                      ReservaRepositoryPort reservaRepository,
                                      PrestamoDomainService domainService,
                                      ConsultarUsuarioPort consultarUsuarioPort,
                                      ConsultarMultasPort consultarMultasPort,
                                      ConsultarStockPort consultarStockPort,
                                      ActualizarStockPort actualizarStockPort,
                                      RegistrarMultaPort registrarMultaPort,
                                      PagarMultasPort pagarMultasPort) {
        this.prestamoRepository = prestamoRepository;
        this.reservaRepository = reservaRepository;
        this.domainService = domainService;
        this.consultarUsuarioPort = consultarUsuarioPort;
        this.consultarMultasPort = consultarMultasPort;
        this.consultarStockPort = consultarStockPort;
        this.actualizarStockPort = actualizarStockPort;
        this.registrarMultaPort = registrarMultaPort;
        this.pagarMultasPort = pagarMultasPort;
    }

    @Override
    public PrestamoResponse prestar(PrestarLibroRequest request) {
        String usuarioId = request.getUsuarioId();
        String libroCodigo = request.getLibroCodigo();

        if (!consultarUsuarioPort.existsById(usuarioId)) {
            throw new IllegalArgumentException("Usuario no existe");
        }
        if (consultarMultasPort.tieneMultasPendientes(usuarioId)) {
            throw new IllegalStateException("El usuario tiene multas pendientes. Debe pagarlas para solicitar préstamo.");
        }
        if (!domainService.puedeSolicitarPrestamo(usuarioId)) {
            throw new IllegalStateException("El usuario ya tiene 3 préstamos activos.");
        }
        if (domainService.yaTienePrestamoActivoDelLibro(usuarioId, libroCodigo)) {
            throw new IllegalStateException("Ya tiene un préstamo activo de este libro.");
        }

        Integer stock = consultarStockPort.getStockByCodigo(libroCodigo)
                .orElseThrow(() -> new IllegalArgumentException("Libro no encontrado"));
        if (stock <= 0) {
            var reservaActiva = reservaRepository.findActivaByLibro(libroCodigo);
            if (reservaActiva.isPresent() && !reservaActiva.get().getUsuarioId().equals(usuarioId)) {
                throw new IllegalStateException("El libro está reservado por otro usuario.");
            }
        }

        actualizarStockPort.decrementarStock(libroCodigo);

        reservaRepository.findActivaByUsuarioAndLibro(usuarioId, libroCodigo)
                .ifPresent(reserva -> {
                    reserva.completar();
                    reservaRepository.save(reserva);
                });

        String idPrestamoStr = "P" + (contadorPrestamos++);
        IdPrestamo idPrestamo = new IdPrestamo(idPrestamoStr);
        PeriodoPrestamo periodo = new PeriodoPrestamo(LocalDate.now(), 14);
        Prestamo prestamo = new Prestamo(idPrestamo, usuarioId, libroCodigo, periodo);
        prestamoRepository.save(prestamo);

        return toPrestamoResponse(prestamo);
    }

    @Override
    public PrestamoResponse devolver(DevolverLibroRequest request) {
        String prestamoId = request.getPrestamoId();
        IdPrestamo id = new IdPrestamo(prestamoId);
        Prestamo prestamo = prestamoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Préstamo no encontrado"));

        if (!prestamo.estaActivo()) {
            throw new IllegalStateException("El préstamo ya fue devuelto");
        }

        LocalDate hoy = LocalDate.now();
        LibroDevueltoEvent evento = prestamo.devolver(hoy);
        prestamoRepository.save(prestamo);
        actualizarStockPort.incrementarStock(prestamo.getLibroCodigo());

        if (evento.getMulta() != null && evento.getMulta().value() > 0) {
            registrarMultaPort.registrarMulta(evento.getUsuarioId(), evento.getMulta().value());
        }

        return toPrestamoResponse(prestamo);
    }

    @Override
    public ReservaResponse reservar(ReservarLibroRequest request) {
        String usuarioId = request.getUsuarioId();
        String libroCodigo = request.getLibroCodigo();

        if (!consultarUsuarioPort.existsById(usuarioId)) {
            throw new IllegalArgumentException("Usuario no existe");
        }
        if (consultarMultasPort.tieneMultasPendientes(usuarioId)) {
            throw new IllegalStateException("No puede reservar porque tiene multas pendientes.");
        }
        if (!consultarStockPort.existsByCodigo(libroCodigo)) {
            throw new IllegalArgumentException("Libro no encontrado");
        }
        if (reservaRepository.findActivaByUsuarioAndLibro(usuarioId, libroCodigo).isPresent()) {
            throw new IllegalStateException("Ya tiene una reserva activa para este libro");
        }

        String idReservaStr = "R" + (contadorReservas++);
        IdReserva idReserva = new IdReserva(idReservaStr);
        Reserva reserva = new Reserva(idReserva, usuarioId, libroCodigo, LocalDate.now());
        reservaRepository.save(reserva);
        return toReservaResponse(reserva);
    }

    @Override
    public void cancelar(CancelarReservaRequest request) {
        IdReserva id = new IdReserva(request.getReservaId());
        Reserva reserva = reservaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Reserva no encontrada"));
        reserva.cancelar();
        reservaRepository.save(reserva);
    }
        // ===== Para préstamos (MostrarPrestamosUseCase) =====
        @Override
        public List<PrestamoResponse> listarTodos() {
            return prestamoRepository.findAll().stream()
                    .map(this::toPrestamoResponse)
                    .collect(Collectors.toList());
        }

        @Override
        public List<PrestamoResponse> listarPorUsuario(String usuarioId) {
            return prestamoRepository.findByUsuarioId(usuarioId).stream()
                    .map(this::toPrestamoResponse)
                    .collect(Collectors.toList());
        }

        // ===== Para reservas (MostrarReservasUseCase) - CON OTROS NOMBRES =====
        @Override
        public List<ReservaResponse> listarTodasReservas() {
            return reservaRepository.findAll().stream()
                    .map(this::toReservaResponse)
                    .collect(Collectors.toList());
        }

        @Override
        public List<ReservaResponse> listarReservasPorUsuario(String usuarioId) {
            return reservaRepository.findActivasByUsuarioId(usuarioId).stream()
                    .map(this::toReservaResponse)
                    .collect(Collectors.toList());
        }

    @Override
    public MultaResponse pagar(PagarMultasRequest request) {
        String usuarioId = request.getUsuarioId();
        if (!consultarUsuarioPort.existsById(usuarioId)) {
            throw new IllegalArgumentException("Usuario no existe");
        }
        double pagado = pagarMultasPort.pagarMultas(usuarioId);
        return new MultaResponse(usuarioId, pagado);
    }

    private PrestamoResponse toPrestamoResponse(Prestamo p) {
        return new PrestamoResponse(
                p.getId().getValue(),
                p.getUsuarioId(),
                p.getLibroCodigo(),
                p.getEstado().toString(),
                p.getPeriodo().getFechaInicio(),
                p.getPeriodo().getFechaLimite(),
                p.getMultaGenerada() != null ? p.getMultaGenerada().value() : 0.0
        );
    }

    private ReservaResponse toReservaResponse(Reserva r) {
        return new ReservaResponse(
                r.getId().getValue(),
                r.getUsuarioId(),
                r.getLibroCodigo(),
                r.getEstado().toString(),
                r.getFechaReserva()
        );
    }
}