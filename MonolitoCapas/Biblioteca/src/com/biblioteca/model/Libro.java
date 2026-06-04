package MonolitoCapas.Biblioteca.src.com.biblioteca.model;

import java.util.UUID;

public class Libro {
    private UUID id;
    private String codigo;
    private String titulo;
    private String autor;
    private int stock;

    public Libro(UUID id, String codigo, String titulo, String autor, int stock) {
        this.id = id;
        this.codigo = codigo;
        this.titulo = titulo;
        this.autor = autor;
        this.stock = stock;
    }

    // Getters y Setters
    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
    public String getCodigo() { return codigo; }
    public void setCodigo(String codigo) { this.codigo = codigo; }
    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }
    public String getAutor() { return autor; }
    public void setAutor(String autor) { this.autor = autor; }
    public int getStock() { return stock; }
    public void setStock(int stock) { this.stock = stock; }
}