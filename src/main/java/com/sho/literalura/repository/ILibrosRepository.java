package com.sho.literalura.repository;

import com.sho.literalura.model.Autor;
import com.sho.literalura.model.Libro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ILibrosRepository extends JpaRepository<Libro,Long> {

    Optional<Libro> findByTituloContainsIgnoreCase(String nombreSerie);

    //Consulta para obtener el nombre del libro
    @Query(value = "SELECT titulo FROM libro WHERE titulo = :tituloLibro", nativeQuery = true)
    String buscaLibroPorTitulo(@Param("tituloLibro") String tituloLibro);

    //Consulta para obtener los libros registrados
    @Query(value = "select CONCAT(titulo,'!',nombre,'!', idioma, '!', descargas) \n" +
            "from libro\n" +
            "join autor on autor.id_autor = libro.id_autor;", nativeQuery = true)

    List <String> buscaLibrosRegistrados();


    @Query(value = "select CONCAT(titulo,'!',nombre,'!', idioma, '!', descargas) \n" +
            "from libro\n" +
            "join autor on autor.id_autor = libro.id_autor WHERE titulo = :tituloLibro", nativeQuery = true)
    List <String> buscaLibroEspecifico(@Param("tituloLibro") String tituloLibro);

    @Query(value = "select CONCAT(titulo,'!',nombre,'!', idioma, '!', descargas) \n" +
            "from libro\n" +
            "join autor on autor.id_autor = libro.id_autor WHERE idioma = :idiomaLibro", nativeQuery = true)
    List <String> buscaLibrosPorIdioma(@Param("idiomaLibro") String idiomaLibro);

}
