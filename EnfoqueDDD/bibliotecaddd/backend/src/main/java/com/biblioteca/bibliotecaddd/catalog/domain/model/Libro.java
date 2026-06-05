package com.biblioteca.bibliotecaddd.catalog.domain.model;

import com.biblioteca.bibliotecaddd.catalog.domain.model.valueobjects.*;

public class Libro {
    private final CodigoLibro codigo;
    private Titulo titulo;
    private Autor autor;
    private Stock stock;

    public Libro(CodigoLibro codigo, Titulo titulo, Autor autor, Stock stock) {
        this.codigo = codigo;
        this.titulo = titulo;
        this.autor = autor;
        this.stock = stock;
    }

    // Getters
    public CodigoLibro getCodigo() { return codigo; }
    public Titulo getTitulo() { return titulo; }
    public Autor getAutor() { return autor; }
    public Stock getStock() { return stock; }

    // Comportamiento: editar
    public void actualizarDatos(Titulo nuevoTitulo, Autor nuevoAutor) {
        this.titulo = nuevoTitulo;
        this.autor = nuevoAutor;
    }

    public void actualizarStock(Stock nuevoStock) {
        this.stock = nuevoStock;
    }

    // Operaciones para prestar/devolver (serán invocadas desde el contexto de préstamos a través de puertos)
    public void prestarEjemplar() {
        this.stock = this.stock.decrementar(1);
    }

    public void devolverEjemplar() {
        this.stock = this.stock.incrementar(1);
    }

    public boolean hayStockDisponible() {
        return this.stock.isDisponible();
    }
}