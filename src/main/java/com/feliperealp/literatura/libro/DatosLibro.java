package com.feliperealp.literatura.libro;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.feliperealp.literatura.autor.DatosAutor;

import java.util.List;

public record DatosLibro(
        Long id,
        @JsonAlias("title") String titulo,
        @JsonAlias("authors") List<DatosAutor> autores,
        @JsonAlias("languages") List<String> idiomas,
        @JsonAlias("download_count") Long descargas
) {

    public String mostrarFormato() {
        String autor = autores.isEmpty() ? "Sin autor" : autores.get(0).nombre();
        String idioma = idiomas.isEmpty() ? "Sin idioma" : idiomas.get(0);
        return ">>>>>> LIBRO <<<<<<\n" +
                "Titulo: " + titulo + "\n" +
                "Autor: " + autor + "\n" +
                "Idioma: " + idioma + "\n" +
                "Descargas: " + descargas + "\n";
    }

}
