package com.aluracursos.literalura.challenge.repository;

import com.aluracursos.literalura.challenge.entity.Libro;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface LibroRepository extends JpaRepository<Libro, Long> {
    
    long countByIdiomasContaining(String idioma);
    List<Libro> findTop10ByOrderByDescargasDesc();
}