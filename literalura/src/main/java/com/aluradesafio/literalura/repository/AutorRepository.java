package com.aluradesafio.literalura.repository;

import com.aluradesafio.literalura.model.Autor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AutorRepository extends JpaRepository<Autor, Long>{
}
