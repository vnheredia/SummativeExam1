package com.biblioteca.bibliotecaddd.loans.infrastructure.adapter.input.rest;

import com.biblioteca.bibliotecaddd.loans.application.dto.*;
import com.biblioteca.bibliotecaddd.loans.application.port.input.*;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/prestamos")
public class PrestamoController {

    private final PrestarLibroUseCase prestarLibro;
    private final DevolverLibroUseCase devolverLibro;
    private final ReservarLibroUseCase reservarLibro;
    private final CancelarReservaUseCase cancelarReserva;
    private final MostrarPrestamosUseCase mostrarPrestamos;
    private final MostrarReservasUseCase mostrarReservas;
    private final PagarMultasUseCase pagarMultas;

    public PrestamoController(PrestarLibroUseCase prestarLibro,
                              DevolverLibroUseCase devolverLibro,
                              ReservarLibroUseCase reservarLibro,
                              CancelarReservaUseCase cancelarReserva,
                              MostrarPrestamosUseCase mostrarPrestamos,
                              MostrarReservasUseCase mostrarReservas,
                              PagarMultasUseCase pagarMultas) {
        this.prestarLibro = prestarLibro;
        this.devolverLibro = devolverLibro;
        this.reservarLibro = reservarLibro;
        this.cancelarReserva = cancelarReserva;
        this.mostrarPrestamos = mostrarPrestamos;
        this.mostrarReservas = mostrarReservas;
        this.pagarMultas = pagarMultas;
    }

    @PostMapping("/prestar")
    public ResponseEntity<PrestamoResponse> prestar(@Valid @RequestBody PrestarLibroRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(prestarLibro.prestar(request));
    }

    @PutMapping("/devolver")
    public ResponseEntity<PrestamoResponse> devolver(@Valid @RequestBody DevolverLibroRequest request) {
        return ResponseEntity.ok(devolverLibro.devolver(request));
    }

    @PostMapping("/reservar")
    public ResponseEntity<ReservaResponse> reservar(@Valid @RequestBody ReservarLibroRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(reservarLibro.reservar(request));
    }

    @PutMapping("/cancelar-reserva")
    public ResponseEntity<Void> cancelarReserva(@Valid @RequestBody CancelarReservaRequest request) {
        cancelarReserva.cancelar(request);
        return ResponseEntity.noContent().build();
    }

    // CORREGIDO: ahora retorna List<PrestamoResponse> (no ReservaResponse)
    @GetMapping("/prestamos")
    public ResponseEntity<List<PrestamoResponse>> listarPrestamos(@RequestParam(required = false) String usuarioId) {
        if (usuarioId != null && !usuarioId.isBlank()) {
            return ResponseEntity.ok(mostrarPrestamos.listarPorUsuario(usuarioId));
        }
        return ResponseEntity.ok(mostrarPrestamos.listarTodos());
    }

    @GetMapping("/reservas")
    public ResponseEntity<List<ReservaResponse>> listarReservas(@RequestParam(required = false) String usuarioId) {
        if (usuarioId != null && !usuarioId.isBlank()) {
            return ResponseEntity.ok(mostrarReservas.listarReservasPorUsuario(usuarioId));
        }
        return ResponseEntity.ok(mostrarReservas.listarTodasReservas());
    }

    @PostMapping("/pagar-multas")
    public ResponseEntity<MultaResponse> pagarMultas(@Valid @RequestBody PagarMultasRequest request) {
        return ResponseEntity.ok(pagarMultas.pagar(request));
    }
}