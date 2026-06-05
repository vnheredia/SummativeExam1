package com.biblioteca.bibliotecaddd.users.infrastructure.adapter.output.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioJpaRepository extends JpaRepository<UsuarioJpaEntity, String> {
}