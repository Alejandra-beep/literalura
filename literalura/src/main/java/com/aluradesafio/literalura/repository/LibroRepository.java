package com.aluradesafio.literalura.repository;

import com.aluradesafio.literalura.model.Libro;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LibroRepository extends JpaRepository<Libro, Long> {
    //para buscar series por titulo
    Optional<Libro> findByTituloContainsIgnoreCase(String titulo);

}
