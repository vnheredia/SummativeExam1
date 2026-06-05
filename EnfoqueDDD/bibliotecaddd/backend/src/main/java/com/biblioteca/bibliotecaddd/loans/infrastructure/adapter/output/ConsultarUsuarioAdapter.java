package com.biblioteca.bibliotecaddd.loans.infrastructure.adapter.output;

import com.biblioteca.bibliotecaddd.users.application.port.output.UsuarioRepositoryPort;
import com.biblioteca.bibliotecaddd.users.application.port.output.ConsultarUsuarioPort;
import com.biblioteca.bibliotecaddd.users.domain.model.valueobjects.UserId;
import org.springframework.stereotype.Component;

@Component
public class ConsultarUsuarioAdapter implements ConsultarUsuarioPort {

    private final UsuarioRepositoryPort usuarioRepository; // ← cambiado

    public ConsultarUsuarioAdapter(UsuarioRepositoryPort usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public boolean existsById(String userId) {
        return usuarioRepository.existsById(new UserId(userId));
    }
}