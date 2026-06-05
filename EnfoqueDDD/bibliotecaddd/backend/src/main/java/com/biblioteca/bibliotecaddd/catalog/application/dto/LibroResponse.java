package com.biblioteca.bibliotecaddd.catalog.application.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LibroResponse {
    private String codigo;
    private String titulo;
    private String autor;
    private int stock;
}