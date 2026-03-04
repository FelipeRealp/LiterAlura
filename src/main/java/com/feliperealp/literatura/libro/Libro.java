package com.feliperealp.literatura.libro;


import com.feliperealp.literatura.autor.Autor;
import jakarta.persistence.*;

@Entity
@Table(name = "libros")
public class Libro {

    @Id
    private Long id;

    @Column(unique = true)
    private String titulo;
    private String idioma;
    private Double numeroDeDescargas;

    @ManyToOne
    @JoinColumn(name = "autor_id") // Esta es la columna que une ambas tablas
    private Autor autor;

    public Libro() {}

    public Libro(DatosLibro datosLibro) {
        this.id = datosLibro.id();
        this.titulo = datosLibro.titulo();
        this.idioma = datosLibro.idiomas().get(0);
        this.numeroDeDescargas = Double.valueOf(datosLibro.descargas());
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getIdioma() {
        return idioma;
    }

    public void setIdioma(String idioma) {
        this.idioma = idioma;
    }

    public Double getNumeroDeDescargas() {
        return numeroDeDescargas;
    }

    public void setNumeroDeDescargas(Double numeroDeDescargas) {
        this.numeroDeDescargas = numeroDeDescargas;
    }

    public Autor getAutor() {
        return autor;
    }

    public void setAutor(Autor autor) {
        this.autor = autor;
    }

    @Override
    public String toString() {
        return  ">>>>>> LIBRO <<<<<<\n" +
                "Titulo: " + titulo + "\n" +
                "Autor: " + autor.getNombre() + "\n" +
                "Idioma: " + idioma.toUpperCase() + "\n" +
                "Descargas: " + numeroDeDescargas + "\n";

    }
}
