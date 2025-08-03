package com.aluracursos.literalura.challenge.dto;

import java.util.List;

public record LibroDto(
    Long id,
    String title,
    String authors,
    Integer birth_year,
    Integer death_year,
    List<String> languages,
    Integer download_count
) {}