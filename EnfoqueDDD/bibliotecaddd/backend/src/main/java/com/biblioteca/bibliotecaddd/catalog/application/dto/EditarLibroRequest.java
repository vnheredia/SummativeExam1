package com.biblioteca.bibliotecaddd.catalog.application.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public class EditarLibroRequest {
    @NotBlank private String titulo;
    @NotBlank private String autor;
    @Min(0) private int stock;

    public EditarLibroRequest() {}
    public EditarLibroRequest(String titulo, String autor, int stock) {
        this.titulo = titulo;
        this.autor = autor;
        this.stock = stock;
    }
    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }
    public String getAutor() { return autor; }
    public void setAutor(String autor) { this.autor = autor; }
    public int getStock() { return stock; }
    public void setStock(int stock) { this.stock = stock; }
}