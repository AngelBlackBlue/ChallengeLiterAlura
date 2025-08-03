package com.aluracursos.literalura.challenge.service;

import com.aluracursos.literalura.challenge.dto.ApiResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LibrosApiService {
    
    private static final String API_BASE_URL = "https://gutendex.com/books";
    
    @Autowired
    private HttpClientService httpClientService;
    
    @Autowired
    private JsonService jsonService;
    
    public ApiResponseDto buscarLibrosPorTitulo(String titulo) {
        String url = API_BASE_URL + "?search=" + titulo.replace(" ", "%20");
        String json = httpClientService.obtenerDatos(url);
        return jsonService.parseApiResponse(json);
    }
    

}