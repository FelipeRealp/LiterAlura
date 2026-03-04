package com.feliperealp.literatura.libro;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LibroRepository extends JpaRepository<Libro, Long> {

    @Query("SELECT DISTINCT l.idioma FROM Libro l WHERE l.idioma IS NOT NULL")
    List<String> buscarIdiomasPresentes();

    List<Libro> findByIdioma(String idioma);

}
