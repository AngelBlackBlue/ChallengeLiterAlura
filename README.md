# 📚 LiterAlura - Challenge Alura

Una aplicación de consola desarrollada en Java con Spring Boot que permite buscar y gestionar información de libros utilizando la API de Gutendex.

## 🚀 Características

- **Búsqueda de libros**: Busca libros por título utilizando la API de Gutendex
- **Gestión de autores**: Lista todos los autores registrados en la base de datos
- **Filtrado por año**: Encuentra autores que estaban vivos en un año específico
- **Estadísticas**: Visualiza estadísticas de libros por idioma
- **Persistencia**: Almacena libros y autores en base de datos PostgreSQL

## 🛠️ Tecnologías Utilizadas

![Java](https://img.shields.io/badge/Java-21-blue?logo=java)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.5.4-brightgreen?logo=spring)
![Spring Data JPA](https://img.shields.io/badge/Spring%20Data%20JPA-3.5.4-yellow?logo=spring)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-Database-blue?logo=postgresql)
![Jackson](https://img.shields.io/badge/Jackson-JSON-orange?logo=json)
![Maven](https://img.shields.io/badge/Maven-3.9+-orange?logo=apache-maven)
![Gutendex API](https://img.shields.io/badge/Gutendex%20API-External-lightblue?logo=api)

## 📋 Prerrequisitos

- Java 21 o superior
- PostgreSQL instalado y ejecutándose
- Maven 3.6 o superior

## ⚙️ Configuración

1. **Clona el repositorio**
   ```bash
   git clone <url-del-repositorio>
   cd ChallengeLiterAlura
   ```

2. **Configura la base de datos**
   
   Crea una base de datos PostgreSQL y actualiza el archivo `src/main/resources/application.properties`:
   ```properties
   spring.datasource.url=jdbc:postgresql://localhost:5432/literalura
   spring.datasource.username=tu_usuario
   spring.datasource.password=tu_contraseña
   spring.jpa.hibernate.ddl-auto=update
   ```

3. **Ejecuta la aplicación**
   ```bash
   ./mvnw spring-boot:run
   ```

## 🎯 Funcionalidades

### Menú Principal
```
=== MENÚ GUTENDEX ===
1. Buscar libro por título
2. Listar autores
3. Listar autores vivos en determinado año
4. Estadísticas por idioma
0. Salir
```

### 1. Buscar libro por título
- Busca libros en la API de Gutendex
- Guarda automáticamente los resultados en la base de datos
- Muestra información detallada de cada libro encontrado

### 2. Listar autores
- Muestra todos los autores almacenados en la base de datos
- Incluye información de nacimiento y fallecimiento

### 3. Listar autores vivos en determinado año
- Utiliza **derived queries** de Spring Data JPA
- Valida la entrada del usuario
- Filtra autores que estaban vivos en el año especificado

### 4. Estadísticas por idioma
- Muestra conteo de libros por idioma
- Soporta: Inglés, Español, Francés, Portugués

## 🏗️ Arquitectura del Proyecto

```
src/main/java/com/aluracursos/literalura/challenge/
├── dto/
│   ├── ApiResponseDto.java
│   └── LibroDto.java
├── entity/
│   ├── Autor.java
│   └── Libro.java
├── repository/
│   ├── AutorRepository.java
│   └── LibroRepository.java
├── service/
│   └── LibrosApiService.java
└── ChallengeApplication.java
```

## 🔍 Características Técnicas

### Derived Queries
El proyecto utiliza derived queries de Spring Data JPA para consultas complejas:
```java
List<Autor> findByAnioNacimientoLessThanEqualAndAnioFallecimientoIsNullOrAnioNacimientoLessThanEqualAndAnioFallecimientoGreaterThanEqual(Integer anio1, Integer anio2, Integer anio3);
```

### Validación de Datos
- Validación de entrada numérica
- Verificación de rangos de años (0-2024)
- Manejo de cadenas vacías
- Mensajes de error descriptivos

### Manejo de Errores
- Try-catch para operaciones de red
- Validación de datos de entrada
- Manejo graceful de errores de base de datos

## 📊 Modelo de Datos

### Entidad Autor
- `id`: Identificador único
- `nombre`: Nombre del autor
- `anioNacimiento`: Año de nacimiento
- `anioFallecimiento`: Año de fallecimiento (nullable)

### Entidad Libro
- `id`: Identificador único
- `titulo`: Título del libro
- `idiomas`: Idiomas disponibles
- `numeroDescargas`: Número de descargas
- `autor`: Relación con Autor

## 🌐 API Externa

El proyecto consume la **API de Gutendex** (https://gutendex.com/) para obtener información de libros del Proyecto Gutenberg.

## 🚦 Cómo Usar

1. Ejecuta la aplicación
2. Selecciona una opción del menú (1-4)
3. Sigue las instrucciones en pantalla
4. Para salir, selecciona la opción 0

## 🤝 Contribuciones

Este proyecto fue desarrollado como parte del programa ONE de Alura Latam.

## 📝 Licencia

Proyecto educativo desarrollado para el Challenge de Alura.

---

**Desarrollado con ❤️ como parte del programa ONE - Oracle Next Education**