package com.sho.literalura.repository;

import com.sho.literalura.model.Autor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface IAutoresRepository extends JpaRepository<Autor,Long> {

    //Consulta para obtener el id del autor en base a su nombre
    @Query(value = "SELECT id_autor FROM autor WHERE nombre = :nombreAutor", nativeQuery = true)
    int buscaAutorPorNombre(@Param("nombreAutor") String nombreAutor);
    //Haciendo uso de @Param, Spring se asegura de colocar el parámetro en donde debe ir

    //Al ser de tipo serial, se obtiene el ID máximo y se suma 1 para obtener el ID siguiente
    @Query(value = "SELECT (MAX(id_autor)) + 1 FROM autor", nativeQuery = true)
    int buscaSiguienteID();

    @Query(value = "SELECT CONCAT(nombre, ';', fecha_nacimiento, ';', fecha_muerte, ';') as datos_generales, array_agg(libro.titulo) AS libros\n" +
            "FROM autor\n" +
            "LEFT JOIN libro\n" +
            "ON autor.id_autor = libro.id_autor\n" +
            "GROUP BY autor.id_autor;", nativeQuery = true)
    List<String> buscaAutoresRegistrados();

    @Query(value = "select buscaAutorPorAnno(:anno)", nativeQuery = true)
    List<String> buscaAutorVivoEnAnno(@Param("anno") String anno);

    /*Función empleada:
CREATE OR REPLACE FUNCTION buscaAutorPorAnno(anno character varying)
RETURNS SETOF TEXT
AS $$
BEGIN
    RETURN QUERY
    SELECT
        CONCAT(
            nombre, ';',
            fecha_nacimiento, ';',
            fecha_muerte, ';',
            array_to_string(array_agg(libro.titulo), ', ')
        ) as datos_generales
    FROM
        autor
    LEFT JOIN
        libro ON autor.id_autor = libro.id_autor
    WHERE
        fecha_nacimiento < anno
    GROUP BY
        autor.id_autor;
END;
$$ LANGUAGE plpgsql;

drop function buscaAutorPorAnno;

select buscaAutorPorAnno('2000')
*/

}
