Literalura - Catálogo de Libros
Literalura es un desafío de programación que consiste en crear un catálogo de libros con persistencia de datos, consumiendo la API de Gutendex. El proyecto permite buscar libros por título, gestionar autores y filtrar por idiomas, todo desde una interfaz de consola robusta y validada.

🚀 Características
Búsqueda inteligente: Consulta libros directamente desde la API Gutendex.

Persistencia de datos: Almacena libros y autores en una base de datos PostgreSQL.

Gestión de duplicados: Valida que no se repitan libros ni autores mediante el ID de la API y lógica de negocio.

Consultas avanzadas:

Listado de todos los libros registrados.

Listado de autores con sus respectivos libros.

Filtro de autores vivos en un año determinado (usando JPQL).

Filtro dinámico de idiomas basado en los registros existentes en la DB.

Robustez: Manejo de excepciones para entradas de usuario inválidas (letras en lugar de números).

🛠️ Tecnologías Utilizadas
Java 17

Spring Boot 4.0.0

Spring Data JPA: Para la gestión de la base de datos.

PostgreSQL: Base de datos relacional.

Jackson: Para la manipulación de datos JSON de la API.
