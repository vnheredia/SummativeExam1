package com.biblioteca.bibliotecaddd.catalog.infrastructure.adapter.output.persistence;

import jakarta.persistence.*;

@Entity
@Table(name = "libros")
public class LibroJpaEntity {
    @Id
    private String codigo;
    private String titulo;
    private String autor;
    private int stock;

    public LibroJpaEntity() {}

    public LibroJpaEntity(String codigo, String titulo, String autor, int stock) {
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