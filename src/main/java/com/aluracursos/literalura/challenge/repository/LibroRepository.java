package com.aluracursos.literalura.challenge.repository;

import com.aluracursos.literalura.challenge.entity.Libro;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LibroRepository extends JpaRepository<Libro, Long> {
}