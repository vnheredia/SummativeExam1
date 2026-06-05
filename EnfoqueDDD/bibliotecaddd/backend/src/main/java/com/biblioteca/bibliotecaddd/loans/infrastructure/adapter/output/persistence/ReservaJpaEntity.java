package com.biblioteca.bibliotecaddd.loans.infrastructure.adapter.output.persistence;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDate;

@Entity
@Table(name = "reservas")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReservaJpaEntity {
    @Id
    private String id;
    private String usuarioId;
    private String libroCodigo;
    private String estado;
    private LocalDate fechaReserva;
}