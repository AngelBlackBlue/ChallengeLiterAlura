package com.aluracursos.literalura.challenge.service;

import com.aluracursos.literalura.challenge.dto.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import java.util.stream.Collectors;

@Service
public class JsonService {
    
    private final ObjectMapper objectMapper = new ObjectMapper();
    
    public ApiResponseDto parseApiResponse(String json) {
        try {
            ApiRawResponseDto raw = objectMapper.readValue(json, ApiRawResponseDto.class);
            return new ApiResponseDto(
                raw.count(),
                raw.next(),
                raw.previous(),
                raw.results().stream().map(this::convertirLibro).collect(Collectors.toList())
            );
        } catch (Exception e) {
            throw new RuntimeException("Error al parsear JSON: " + e.getMessage());
        }
    }
    
    private LibroDto convertirLibro(LibroRawDto raw) {
        String autor = raw.authors().isEmpty() ? "Desconocido" : raw.authors().get(0).name();
        Integer birthYear = raw.authors().isEmpty() ? null : raw.authors().get(0).birth_year();
        Integer deathYear = raw.authors().isEmpty() ? null : raw.authors().get(0).death_year();
        
        return new LibroDto(
            raw.id(),
            raw.title(),
            autor,
            birthYear,
            deathYear,
            raw.languages(),
            raw.download_count()
        );
    }
}