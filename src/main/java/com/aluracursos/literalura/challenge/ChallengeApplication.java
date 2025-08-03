package com.aluracursos.literalura.challenge;

import com.aluracursos.literalura.challenge.dto.ApiResponseDto;
import com.aluracursos.literalura.challenge.dto.LibroDto;
import com.aluracursos.literalura.challenge.service.LibrosApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import java.util.Scanner;

@SpringBootApplication(exclude = {org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration.class})
public class ChallengeApplication implements CommandLineRunner {

	@Autowired
	private LibrosApiService librosApiService;

	public static void main(String[] args) {
		SpringApplication.run(ChallengeApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Scanner scanner = new Scanner(System.in);
		int opcion;
		
		do {
			mostrarMenu();
			try {
				opcion = Integer.parseInt(scanner.nextLine());
			} catch (NumberFormatException e) {
				System.out.println("Por favor ingresa un número válido");
				opcion = -1;
				continue;
			}
			
			switch (opcion) {
				case 1:
					System.out.print("Ingresa el nombre del libro: ");
					String titulo = scanner.nextLine();
					buscarPorTitulo(titulo);
					break;
				case 0:
					System.out.println("¡Hasta luego!");
					break;
				default:
					System.out.println("Opción inválida");
			}
		} while (opcion != 0);
		
		scanner.close();
	}
	
	private void mostrarMenu() {
		System.out.println("\n=== MENÚ GUTENDEX ===");
		System.out.println("1. Buscar libro por título");
		System.out.println("0. Salir");
		System.out.print("Elige una opción: ");
	}
	
	private void buscarPorTitulo(String titulo) {
		try {
			ApiResponseDto respuesta = librosApiService.buscarLibrosPorTitulo(titulo);
			System.out.println("\nEncontrados: " + respuesta.count() + " libros");
			respuesta.results().forEach(this::mostrarLibro);
		} catch (Exception e) {
			System.err.println("Error: " + e.getMessage());
		}
	}
	
	
	private void mostrarLibro(LibroDto libro) {
		System.out.println("\n--- " + libro.title() + " ---");
		System.out.println("Autores: " + libro.authors() + " (" + libro.birth_year() + "-" + libro.death_year() + ")");
		System.out.println("Idiomas: " + String.join(", ", libro.languages()));
		System.out.println("Descargas: " + libro.download_count());
	}
}
