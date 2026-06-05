package com.biblioteca.bibliotecaddd.users.infrastructure.adapter.input.rest;

import com.biblioteca.bibliotecaddd.users.application.dto.EditarUsuarioRequest;
import com.biblioteca.bibliotecaddd.users.application.dto.RegistrarUsuarioRequest;
import com.biblioteca.bibliotecaddd.users.application.dto.UsuarioResponse;
import com.biblioteca.bibliotecaddd.users.application.port.input.*;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    private final RegistrarUsuarioUseCase registrarUsuario;
    private final BuscarUsuarioUseCase buscarUsuario;
    private final EditarUsuarioUseCase editarUsuario;
    private final EliminarUsuarioUseCase eliminarUsuario;

    public UsuarioController(RegistrarUsuarioUseCase registrarUsuario,
                             BuscarUsuarioUseCase buscarUsuario,
                             EditarUsuarioUseCase editarUsuario,
                             EliminarUsuarioUseCase eliminarUsuario) {
        this.registrarUsuario = registrarUsuario;
        this.buscarUsuario = buscarUsuario;
        this.editarUsuario = editarUsuario;
        this.eliminarUsuario = eliminarUsuario;
    }

    @PostMapping
    public ResponseEntity<UsuarioResponse> registrar(@Valid @RequestBody RegistrarUsuarioRequest request) {
        UsuarioResponse response = registrarUsuario.registrar(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<List<UsuarioResponse>> listar() {
        return ResponseEntity.ok(buscarUsuario.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioResponse> buscar(@PathVariable String id) {
        return buscarUsuario.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<UsuarioResponse> editar(@PathVariable String id,
                                                  @Valid @RequestBody EditarUsuarioRequest request) {
        UsuarioResponse response = editarUsuario.editar(id, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable String id) {
        eliminarUsuario.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}