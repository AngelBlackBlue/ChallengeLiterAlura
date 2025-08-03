package com.aluracursos.literalura.challenge.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record LibroRawDto(
    Long id,
    String title,
    List<AutorDto> authors,
    List<String> languages,
    Integer download_count
) {}