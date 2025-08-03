package com.aluracursos.literalura.challenge.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "libros")
public class Libro {
    
    @Id
    private Long id;
    
    private String titulo;
    private String idiomas;
    private Integer descargas;
    
    @ManyToOne
    @JoinColumn(name = "autor_id")
    private Autor autor;
    
    public Libro() {}
    
    public Libro(Long id, String titulo, String idiomas, Integer descargas, Autor autor) {
        this.id = id;
        this.titulo = titulo;
        this.idiomas = idiomas;
        this.descargas = descargas;
        this.autor = autor;
    }
    
    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }
    
    public String getIdiomas() { return idiomas; }
    public void setIdiomas(String idiomas) { this.idiomas = idiomas; }
    
    public Integer getDescargas() { return descargas; }
    public void setDescargas(Integer descargas) { this.descargas = descargas; }
    
    public Autor getAutor() { return autor; }
    public void setAutor(Autor autor) { this.autor = autor; }
}