package com.aluracursos.literalura.challenge;

import com.aluracursos.literalura.challenge.dto.ApiResponseDto;
import com.aluracursos.literalura.challenge.dto.LibroDto;
import com.aluracursos.literalura.challenge.entity.Autor;
import com.aluracursos.literalura.challenge.entity.Libro;
import com.aluracursos.literalura.challenge.repository.AutorRepository;
import com.aluracursos.literalura.challenge.repository.LibroRepository;
import com.aluracursos.literalura.challenge.service.LibrosApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import java.util.List;
import java.util.Scanner;

@SpringBootApplication
public class ChallengeApplication implements CommandLineRunner {

	@Autowired
	private LibrosApiService librosApiService;
	
	@Autowired
	private AutorRepository autorRepository;
	
	@Autowired
	private LibroRepository libroRepository;

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
				case 2:
					listarAutores();
					break;
				case 3:
					System.out.print("Ingresa el año: ");
					try {
						Integer anio = Integer.parseInt(scanner.nextLine());
						listarAutoresVivos(anio);
					} catch (NumberFormatException e) {
						System.out.println("Año inválido");
					}
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
		System.out.println("2. Listar autores");
		System.out.println("3. Listar autores vivos en determinado año");
		System.out.println("0. Salir");
		System.out.print("Elige una opción: ");
	}
	
	private void buscarPorTitulo(String titulo) {
		try {
			ApiResponseDto respuesta = librosApiService.buscarLibrosPorTitulo(titulo);
			System.out.println("\nEncontrados: " + respuesta.count() + " libros");
			respuesta.results().forEach(libro -> {
				mostrarLibro(libro);
				guardarLibro(libro);
			});
		} catch (Exception e) {
			System.err.println("Error: " + e.getMessage());
		}
	}
	
	private void listarAutores() {
		List<Autor> autores = autorRepository.findAll();
		if (autores.isEmpty()) {
			System.out.println("\nNo hay autores registrados");
		} else {
			System.out.println("\n=== AUTORES REGISTRADOS ===");
			autores.forEach(this::mostrarAutor);
		}
	}
	
	private void listarAutoresVivos(Integer anio) {
		List<Autor> autores = autorRepository.findAutoresVivosEnAnio(anio);
		if (autores.isEmpty()) {
			System.out.println("\nNo hay autores vivos en el año " + anio);
		} else {
			System.out.println("\n=== AUTORES VIVOS EN " + anio + " ===");
			autores.forEach(this::mostrarAutor);
		}
	}
	
	private void guardarLibro(LibroDto libroDto) {
		try {
			Autor autor = autorRepository.findByNombre(libroDto.authors())
				.orElseGet(() -> {
					Autor nuevoAutor = new Autor(libroDto.authors(), libroDto.birth_year(), libroDto.death_year());
					return autorRepository.save(nuevoAutor);
				});
			
			Libro libro = new Libro(libroDto.id(), libroDto.title(), 
				String.join(", ", libroDto.languages()), libroDto.download_count(), autor);
			libroRepository.save(libro);
		} catch (Exception e) {
			// Ignorar errores de duplicados
		}
	}
	
	private void mostrarAutor(Autor autor) {
		System.out.println("\nAutor: " + autor.getNombre());
		System.out.println("Nacimiento: " + autor.getAnioNacimiento());
		System.out.println("Fallecimiento: " + (autor.getAnioFallecimiento() != null ? autor.getAnioFallecimiento() : "Vivo"));
	}
	
	
	private void mostrarLibro(LibroDto libro) {
		System.out.println("\n--- " + libro.title() + " ---");
		System.out.println("Autores: " + libro.authors() + " (" + libro.birth_year() + "-" + libro.death_year() + ")");
		System.out.println("Idiomas: " + String.join(", ", libro.languages()));
		System.out.println("Descargas: " + libro.download_count());
	}
}
