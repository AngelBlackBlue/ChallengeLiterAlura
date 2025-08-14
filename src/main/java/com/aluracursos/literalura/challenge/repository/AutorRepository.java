package com.aluracursos.literalura.challenge.repository;

import com.aluracursos.literalura.challenge.entity.Autor;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface AutorRepository extends JpaRepository<Autor, Long> {
    
    Optional<Autor> findByNombre(String nombre);
    List<Autor> findByNombreContainingIgnoreCase(String nombre);
    
    List<Autor> findByAnioNacimientoLessThanEqualAndAnioFallecimientoIsNullOrAnioNacimientoLessThanEqualAndAnioFallecimientoGreaterThanEqual(Integer anio1, Integer anio2, Integer anio3);
}