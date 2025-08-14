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
import java.util.DoubleSummaryStatistics;
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
						String input = scanner.nextLine().trim();
						if (input.isEmpty()) {
							System.out.println("Debes ingresar un año");
							break;
						}
						Integer anio = Integer.parseInt(input);
						if (anio < 0 || anio > 2024) {
							System.out.println("Año inválido. Ingresa un año entre 0 y 2024");
							break;
						}
						listarAutoresVivos(anio);
					} catch (NumberFormatException e) {
						System.out.println("Año inválido. Ingresa solo números");
					}
					break;
				case 4:
					mostrarEstadisticasPorIdioma();
					break;
				case 5:
					mostrarTop10Libros();
					break;
				case 6:
					System.out.print("Ingresa el nombre del autor: ");
					String nombreAutor = scanner.nextLine().trim();
					if (!nombreAutor.isEmpty()) {
						buscarAutorPorNombre(nombreAutor);
					} else {
						System.out.println("Debes ingresar un nombre");
					}
					break;
				case 7:
					mostrarEstadisticasGenerales();
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
		System.out.println("4. Estadísticas por idioma");
		System.out.println("5. Top 10 libros más descargados");
		System.out.println("6. Buscar autor por nombre");
		System.out.println("7. Estadísticas generales");
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
		List<Autor> autores = autorRepository.findByAnioNacimientoLessThanEqualAndAnioFallecimientoIsNullOrAnioNacimientoLessThanEqualAndAnioFallecimientoGreaterThanEqual(anio, anio, anio);
		if (autores.isEmpty()) {
			System.out.println("\nNo hay autores vivos en el año " + anio);
		} else {
			System.out.println("\n=== AUTORES VIVOS EN " + anio + " ===");
			autores.forEach(this::mostrarAutor);
		}
	}
	
	private void guardarLibro(LibroDto libroDto) {
		try {
			if (libroRepository.existsById(libroDto.id())) {
				return;
			}
			
			Autor autor = autorRepository.findByNombre(libroDto.authors())
				.orElseGet(() -> autorRepository.save(
					new Autor(libroDto.authors(), libroDto.birth_year(), libroDto.death_year())));
			
			libroRepository.save(new Libro(libroDto.id(), libroDto.title(), 
				String.join(", ", libroDto.languages()), libroDto.download_count(), autor));
		} catch (Exception e) {
			// Ignorar errores
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
	
	private void mostrarEstadisticasPorIdioma() {
		System.out.println("\n=== ESTADÍSTICAS POR IDIOMA ===");
		
		String[] idiomas = {"en", "es", "fr", "pt"};
		String[] nombres = {"Inglés", "Español", "Francés", "Portugués"};
		
		for (int i = 0; i < idiomas.length; i++) {
			long cantidad = libroRepository.countByIdiomasContaining(idiomas[i]);
			System.out.println(nombres[i] + ": " + cantidad + " libros");
		}
	}
	
	private void mostrarTop10Libros() {
		List<Libro> topLibros = libroRepository.findTop10ByOrderByDescargasDesc();
		if (topLibros.isEmpty()) {
			System.out.println("\nNo hay libros registrados");
		} else {
			System.out.println("\n=== TOP 10 LIBROS MÁS DESCARGADOS ===");
			for (int i = 0; i < topLibros.size(); i++) {
				Libro libro = topLibros.get(i);
				System.out.println((i + 1) + ". " + libro.getTitulo() + " - " + libro.getDescargas() + " descargas");
			}
		}
	}
	
	private void buscarAutorPorNombre(String nombre) {
		List<Autor> autores = autorRepository.findByNombreContainingIgnoreCase(nombre);
		if (autores.isEmpty()) {
			System.out.println("\nNo se encontraron autores con el nombre: " + nombre);
		} else {
			System.out.println("\n=== AUTORES ENCONTRADOS ===");
			autores.forEach(this::mostrarAutor);
		}
	}
	
	private void mostrarEstadisticasGenerales() {
		List<Libro> libros = libroRepository.findAll();
		if (libros.isEmpty()) {
			System.out.println("\nNo hay libros registrados");
			return;
		}
		
		DoubleSummaryStatistics stats = libros.stream()
				.mapToDouble(Libro::getDescargas)
				.summaryStatistics();
		
		System.out.println("\n=== ESTADÍSTICAS GENERALES ===");
		System.out.println("Total de libros: " + stats.getCount());
		System.out.println("Promedio de descargas: " + String.format("%.2f", stats.getAverage()));
		System.out.println("Máximo de descargas: " + (long)stats.getMax());
		System.out.println("Mínimo de descargas: " + (long)stats.getMin());
		System.out.println("Total de descargas: " + (long)stats.getSum());
	}
}
