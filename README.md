# Challenge Conversor de Monedas

## Descripción
consiste en crear un catálogo de libros en Java utilizando Spring Boot y Postgres. La aplicación debe permitir a los usuarios buscar libros por título, listar los libros registrados, listar los autores, listar los autores vivos en un año determinado, listar los libros por idioma y manejar la posibilidad de que el usuario ingrese un libro que no existe en la API.

## Elementos usados
- **IntelliJ**
- **JDK 21**
- **Framework Spring**
- **Biblioteca Jackson Databind**
- **PostgreSQL**
- **API Gutendex**

## Descripción de funcionamiento
La aplicación funciona mediante un menú con las opciones: 

                    1 - Buscar libro por título
                    2 - Listar libros registrados
                    3 - Listar autores registrados
                    4 - Listar autores vivos en un año determinado
                    5 - Listar libros por idioma
                    0 - Salir

1. Se hace una solicitud a la API con el título del libro, y en caso de encontrar una coincidencia, se crea el registro del autor (si es que no existe antes) y el libro (en caso de que no haya sido registrado aún) en la base de datos.

2. Se listan los libros registrados en la base de datos (título, idioma, autor y número de descargas)

3. Se listan todos los autores registrados, así como los libros a su nombre

4. Se listan todos los autores que se encuentran vivos dado un determinado año

5. Se listan libros por un determinado idioma

## Base de datos
Se hizo uso de ingeniería inversa para generar las entidades de la base de datos en clases de Java; es decir, primero se creó la base de datos y después, con ayuda de IntelliJ, se generaron las clases correspondientes.
El diagrama de la base de datos se adjunta en una imagen ubidada en la raíz.

