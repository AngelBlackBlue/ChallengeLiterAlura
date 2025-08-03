package com.aluracursos.literalura.challenge.dto;

import java.util.List;

public record ApiRawResponseDto(
    Integer count,
    String next,
    String previous,
    List<LibroRawDto> results
) {}