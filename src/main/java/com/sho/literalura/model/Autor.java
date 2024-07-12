package com.sho.literalura.model;

import jakarta.persistence.*;

import java.sql.Date;
import java.util.List;
import java.util.OptionalDouble;

@Entity
@Table(name = "autor", schema = "public", catalog = "libros")
public class Autor {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id_autor")
    private int idAutor;
    @Basic
    @Column(name = "nombre")
    private String nombre;
    @Basic
    @Column(name = "fecha_nacimiento")
    private String fechaNacimiento;
    @Basic
    @Column(name = "fecha_muerte")
    private String fechaMuerte;



    public Autor(){}

    public Autor(DatosAutor datosAutor){
        this.nombre = datosAutor.nombre();
        this.fechaNacimiento = datosAutor.fechaDeNacimiento();
        this.fechaMuerte = (datosAutor.fechaDeMuerte());
    }


    public int getIdAutor() {
        return idAutor;
    }

    public void setIdAutor(int idAutor) {
        this.idAutor = idAutor;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(String fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getFechaMuerte() {
        return fechaMuerte;
    }

    public void setFechaMuerte(String fechaMuerte) {
        this.fechaMuerte = fechaMuerte;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Autor that = (Autor) o;

        if (idAutor != that.idAutor) return false;
        if (nombre != null ? !nombre.equals(that.nombre) : that.nombre != null) return false;
        if (fechaNacimiento != null ? !fechaNacimiento.equals(that.fechaNacimiento) : that.fechaNacimiento != null)
            return false;
        if (fechaMuerte != null ? !fechaMuerte.equals(that.fechaMuerte) : that.fechaMuerte != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = idAutor;
        result = 31 * result + (nombre != null ? nombre.hashCode() : 0);
        result = 31 * result + (fechaNacimiento != null ? fechaNacimiento.hashCode() : 0);
        result = 31 * result + (fechaMuerte != null ? fechaMuerte.hashCode() : 0);
        return result;
    }
}
