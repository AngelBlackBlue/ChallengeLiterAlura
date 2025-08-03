package com.aluracursos.literalura.challenge.dto;

import java.util.List;

public record ApiResponseDto(
    Integer count,
    String next,
    String previous,
    List<LibroDto> results
) {}