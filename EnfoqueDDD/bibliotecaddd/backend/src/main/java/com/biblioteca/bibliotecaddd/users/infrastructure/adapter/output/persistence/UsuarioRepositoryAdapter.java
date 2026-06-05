package com.biblioteca.bibliotecaddd.users.infrastructure.adapter.output.persistence;

import com.biblioteca.bibliotecaddd.users.application.port.output.UsuarioRepositoryPort;
import com.biblioteca.bibliotecaddd.users.domain.model.Usuario;
import com.biblioteca.bibliotecaddd.users.domain.model.valueobjects.NombreUsuario;
import com.biblioteca.bibliotecaddd.users.domain.model.valueobjects.UserId;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class UsuarioRepositoryAdapter implements UsuarioRepositoryPort {

    private final UsuarioJpaRepository jpaRepository;

    public UsuarioRepositoryAdapter(UsuarioJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public void save(Usuario usuario) {
        UsuarioJpaEntity entity = new UsuarioJpaEntity(
                usuario.getId().getValue(),
                usuario.getNombre().getNombre()
        );
        jpaRepository.save(entity);
    }

    @Override
    public Optional<Usuario> findById(UserId id) {
        return jpaRepository.findById(id.getValue())
                .map(entity -> new Usuario(
                        new UserId(entity.getId()),
                        new NombreUsuario(entity.getNombre())
                ));
    }

    @Override
    public boolean existsById(UserId id) {
        return jpaRepository.existsById(id.getValue());
    }

    @Override
    public void delete(Usuario usuario) {
        jpaRepository.deleteById(usuario.getId().getValue());
    }

    @Override
    public List<Usuario> findAll() {
        return jpaRepository.findAll().stream()
                .map(entity -> new Usuario(
                        new UserId(entity.getId()),
                        new NombreUsuario(entity.getNombre())
                ))
                .collect(Collectors.toList());
    }
}