package com.biblioteca.bibliotecaddd.catalog.infrastructure.adapter.input.rest;

import com.biblioteca.bibliotecaddd.catalog.application.dto.EditarLibroRequest;
import com.biblioteca.bibliotecaddd.catalog.application.dto.LibroResponse;
import com.biblioteca.bibliotecaddd.catalog.application.dto.RegistrarLibroRequest;
import com.biblioteca.bibliotecaddd.catalog.application.port.input.*;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/libros")
public class LibroController {

    private final RegistrarLibroUseCase registrarLibro;
    private final BuscarLibroUseCase buscarLibro;
    private final EditarLibroUseCase editarLibro;
    private final EliminarLibroUseCase eliminarLibro;

    public LibroController(RegistrarLibroUseCase registrarLibro,
                           BuscarLibroUseCase buscarLibro,
                           EditarLibroUseCase editarLibro,
                           EliminarLibroUseCase eliminarLibro) {
        this.registrarLibro = registrarLibro;
        this.buscarLibro = buscarLibro;
        this.editarLibro = editarLibro;
        this.eliminarLibro = eliminarLibro;
    }

    @PostMapping
    public ResponseEntity<LibroResponse> registrar(@Valid @RequestBody RegistrarLibroRequest request) {
        LibroResponse response = registrarLibro.registrar(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<List<LibroResponse>> listar() {
        return ResponseEntity.ok(buscarLibro.listarTodos());
    }

    @GetMapping("/{codigo}")
    public ResponseEntity<LibroResponse> buscar(@PathVariable String codigo) {
        return buscarLibro.buscarPorCodigo(codigo)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{codigo}")
    public ResponseEntity<LibroResponse> editar(@PathVariable String codigo,
                                                @Valid @RequestBody EditarLibroRequest request) {
        LibroResponse response = editarLibro.editar(codigo, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{codigo}")
    public ResponseEntity<Void> eliminar(@PathVariable String codigo) {
        eliminarLibro.eliminar(codigo);
        return ResponseEntity.noContent().build();
    }
}