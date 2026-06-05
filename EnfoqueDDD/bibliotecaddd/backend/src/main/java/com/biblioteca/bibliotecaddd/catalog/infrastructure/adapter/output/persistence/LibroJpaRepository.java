package com.biblioteca.bibliotecaddd.catalog.infrastructure.adapter.output.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

public interface LibroJpaRepository extends JpaRepository<LibroJpaEntity, String> {
}