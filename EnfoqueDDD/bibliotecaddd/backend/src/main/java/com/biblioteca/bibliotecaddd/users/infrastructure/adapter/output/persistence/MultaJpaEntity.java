package com.biblioteca.bibliotecaddd.users.infrastructure.adapter.output.persistence;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "multas")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MultaJpaEntity {
    @Id
    private String usuarioId;
    private double montoPendiente;
}
