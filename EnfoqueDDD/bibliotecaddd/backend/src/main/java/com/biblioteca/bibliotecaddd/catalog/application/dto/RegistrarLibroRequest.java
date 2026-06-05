package com.biblioteca.bibliotecaddd.catalog.application.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public class RegistrarLibroRequest {
    @NotBlank
    @Pattern(regexp = "^[A-Z0-9]{3,10}$", message = "Código inválido")
    private String codigo;

    @NotBlank
    private String titulo;

    @NotBlank
    private String autor;

    @Min(1)
    private int stock;

    public RegistrarLibroRequest() {}

    public RegistrarLibroRequest(String codigo, String titulo, String autor, int stock) {
        this.codigo = codigo;
        this.titulo = titulo;
        this.autor = autor;
        this.stock = stock;
    }

    public String getCodigo() { return codigo; }
    public void setCodigo(String codigo) { this.codigo = codigo; }
    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }
    public String getAutor() { return autor; }
    public void setAutor(String autor) { this.autor = autor; }
    public int getStock() { return stock; }
    public void setStock(int stock) { this.stock = stock; }
}