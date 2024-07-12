package com.sho.literalura.model;

import jakarta.persistence.*;

@Entity
@Table(name = "libro", schema = "public", catalog = "libros")
public class Libro {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id_libro")
    private int idLibro;
    @Basic
    @Column(name = "titulo")
    private String titulo;
    @Basic
    @Column(name = "id_autor")
    private Integer idAutor;
    @Basic
    @Column(name = "idioma")
    private String idioma;
    @Basic
    @Column(name = "descargas")
    private Long descargas;


    public Libro(){}

    public Libro (DatosLibro datosLibro, Autor autor){

        this.titulo = datosLibro.titulo();
        this.idAutor = autor.getIdAutor();
        this.descargas = datosLibro.numeroDeDescargas();

    }

    public int getIdLibro() {
        return idLibro;
    }

    public void setIdLibro(int idLibro) {
        this.idLibro = idLibro;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Integer getIdAutor() {
        return idAutor;
    }

    public void setIdAutor(Integer idAutor) {
        this.idAutor = idAutor;
    }

    public String getIdioma() {
        return idioma;
    }

    public void setIdioma(String idioma) {
        this.idioma = idioma;
    }

    public Long getDescargas() {
        return descargas;
    }

    public void setDescargas(Long descargas) {
        this.descargas = descargas;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Libro that = (Libro) o;

        if (idLibro != that.idLibro) return false;
        if (titulo != null ? !titulo.equals(that.titulo) : that.titulo != null) return false;
        if (idAutor != null ? !idAutor.equals(that.idAutor) : that.idAutor != null) return false;
        if (idioma != null ? !idioma.equals(that.idioma) : that.idioma != null) return false;
        if (descargas != null ? !descargas.equals(that.descargas) : that.descargas != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idLibro;
        result = 31 * result + (titulo != null ? titulo.hashCode() : 0);
        result = 31 * result + (idAutor != null ? idAutor.hashCode() : 0);
        result = 31 * result + (idioma != null ? idioma.hashCode() : 0);
        result = 31 * result + (descargas != null ? descargas.hashCode() : 0);
        return result;
    }
}
