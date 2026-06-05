package com.biblioteca.bibliotecaddd.users.infrastructure.adapter.input.rest;

import com.biblioteca.bibliotecaddd.users.application.port.input.GestionarMultasUseCase;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/usuarios/multas")
public class MultaController {

    private final GestionarMultasUseCase gestionarMultas;

    public MultaController(GestionarMultasUseCase gestionarMultas) {
        this.gestionarMultas = gestionarMultas;
    }

    @GetMapping("/{usuarioId}")
    public ResponseEntity<Map<String, Boolean>> tieneMultas(@PathVariable String usuarioId) {
        boolean tiene = gestionarMultas.tieneMultasPendientes(usuarioId);
        return ResponseEntity.ok(Map.of("tieneMultas", tiene));
    }

    @PostMapping("/{usuarioId}/pagar")
    public ResponseEntity<Map<String, Double>> pagarMultas(@PathVariable String usuarioId) {
        double pagado = gestionarMultas.pagarMultas(usuarioId);
        return ResponseEntity.ok(Map.of("pagado", pagado));
    }
}