package com.biblioteca.bibliotecaddd.catalog.infrastructure.adapter.output.persistence;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "libros")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LibroJpaEntity {
    @Id
    private String codigo;
    private String titulo;
    private String autor;
    private int stock;
}