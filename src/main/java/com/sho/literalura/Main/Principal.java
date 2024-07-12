package com.sho.literalura.Main;

import com.sho.literalura.model.*;
import com.sho.literalura.repository.IAutoresRepository;
import com.sho.literalura.repository.ILibrosRepository;
import com.sho.literalura.service.ConsumoAPI;
import com.sho.literalura.service.ConvierteDatos;
import org.springframework.aop.AopInvocationException;
import org.springframework.dao.EmptyResultDataAccessException;

import javax.sound.midi.Soundbank;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Principal {
    private static final String URL_BASE = "https://gutendex.com/books/"; //Dirección de la API Gtendex
    private static final String URL_TITULO = "/books?search="; //Pedazo de URL para buscar por titulo
    private Scanner lee = new Scanner(System.in); //Simple scanner
    private ConsumoAPI solicitudAPI = new ConsumoAPI(); //Instancia de la clase para hacer una nueva solicitud
    private ConvierteDatos conversor = new ConvierteDatos(); //Instancia de la clase para mapear los resultados
   private DatosAutor nuevoAutor;
    private DatosLibro nuevoLibro;
    int idAutor;
    private ILibrosRepository librosRepository;
    private IAutoresRepository autoresRepository;

    public Principal(ILibrosRepository repositoryLibros, IAutoresRepository repositoryAutores) {
        this.librosRepository = repositoryLibros;
        this.autoresRepository = repositoryAutores;
    }

    public void muestraElMenu() {
        var opcion = ' ';
        while (opcion != '0') {
            var menu = """
                    Por favor seleccione una opción válida:
                    1 - Buscar libro por título
                    2 - Listar libros registrados
                    3 - Listar autores registrados
                    4 - Listar autores vivos en un año determinado
                    5 - Listar libros por idioma
                    0 - Salir
                    """;
            System.out.println(menu);
            opcion = lee.next().charAt(0);

            switch (opcion) {
                case '1':
                    buscaLibroEnAPI();
                    break;
                case '2':
                    listaLibrosRegistrados();
                    break;
                case '3':
                    listaAutoresRegistrados();
                    break;
                case '4':
                    listaAutoresVivosEnAnno();
                    break;
                case '5':
                    listaLibrosPorIdioma();
                    break;

                case '0':
                    System.out.println("Cerrando la aplicación...");
                    break;
                default:
                    System.out.println("Opción inválida");
            }
        }

    }

    private void listaLibrosPorIdioma() {
        String idioma = "";
        var opcion = ' ';
        while (opcion != '0') {
            var menu = """
                    Seleccione un idioma:
                    1 - Español
                    2 - Inglés
                    3 - Francés
                    4 - Portugués
                    0 - Regresar al menú
                    """;
            System.out.println(menu);
            opcion = lee.next().charAt(0);

            switch (opcion) {
                case '1':
                    idioma = "es";
                    break;
                case '2':
                    idioma = "en";
                    break;
                case '3':
                    idioma = "fr";
                    break;
                case '4':
                    idioma = "pt";
                    break;

                case '0':
                    System.out.println("Regresando...");
                    muestraElMenu();
                    break;
                default:
                    System.out.println("Opción inválida");
            }
            List<String> listaLibrosPorIdioma = librosRepository.buscaLibrosPorIdioma(idioma);
            presentaLibros(listaLibrosPorIdioma);
        }
    }

    private void presentaAutores(List<String> listaAutores){

        //Es una lista de libros, por ello se debe iterar cada uno de sus elementos
        if (!listaAutores.isEmpty()) {
            listaAutores.stream().forEach(e -> {
            String[] datosAutor = e.split(";"); // Divide la cadena en un arreglo de cadenas
            System.out.println("----------------------------------");
            System.out.println("Nombre: " + datosAutor[0]);
            //El nombre viene separado en la API, por ello se separó también
            System.out.println("Fecha de nacimiento: " + datosAutor[1]);
            System.out.println("Fecha de nacimiento: " + datosAutor[2]);
            System.out.println("Libros escritos: " + datosAutor[3].replaceFirst(",", ""));
        });
            System.out.println("----------------------------------");
        } else {
            System.out.println("No hay registro de autores que coincidan con los parámetros de búsqueda");
        }


    }

    private void listaAutoresVivosEnAnno() {
        System.out.println("Ingresa el año: ");
        int anno = lee.nextInt();
        List<String> autoresVivos = autoresRepository.buscaAutorVivoEnAnno(String.valueOf(anno));
        presentaAutores(autoresVivos);
    }

    private void listaAutoresRegistrados() {
        List<String> autoresRegistrados = autoresRepository.buscaAutoresRegistrados();
        presentaAutores(autoresRegistrados);
    }

    private void listaLibrosRegistrados() {
        List<String> librosRegistrados = librosRepository.buscaLibrosRegistrados();
        presentaLibros(librosRegistrados);
    }

    private void listaLibroEspecifico(String titulo) {
        List<String> librosRegistrados = librosRepository.buscaLibroEspecifico(titulo);
        presentaLibros(librosRegistrados);
    }

    private void presentaLibros(List<String> librosRegistrados) {

        //Es una lista de libros, por ello se debe iterar cada uno de sus elementos
        if(!librosRegistrados.isEmpty()) {

            librosRegistrados.stream().forEach(e -> {
                String[] datosLibro = e.split("!"); // Divide la cadena en un arreglo de cadenas
                System.out.println("----------------------------------");
                System.out.println("Título del libro: " + datosLibro[0]);
                //El nombre viene separado en la API, por ello se separó también
                System.out.println("Autor: " + datosLibro[1]);
                System.out.println("Idioma: " + datosLibro[2]);
                System.out.println("Número de descargas: " + datosLibro[3]);
            });
            System.out.println("----------------------------------");
        } else {
            System.out.println("No libros que coincidan con los parámetros de búsqueda");
        }
    }


    private void buscaLibroEnAPI(){

        getDatos();



    }

    private void getDatos() {

        //Busqueda de libros por nombre
        System.out.println("Ingrese el nombre del libro que desea buscar");
        var tituloLibro = lee.nextLine();
        var json = solicitudAPI.obtenerDatos(URL_BASE+"?search=" + tituloLibro.replace(" ","+"));
        var datosGenerales = conversor.obtenerDatos(json, Datos.class); //Se llama al conversor

        //Se usa optinal porque puede o no haber resultado
        Optional<DatosLibro> libroBuscado = datosGenerales.resultados().stream()
                //Se verifica si el titulo del libro coincide con el resultado
                .filter(l -> l.titulo().toUpperCase().contains(tituloLibro.toUpperCase()))
                .findFirst();

        if (libroBuscado.isPresent()){
            //Si el libro existe, se instancian los records individualmente para ser manipulados
            nuevoLibro = datosGenerales.resultados().get(0);
            nuevoAutor = nuevoLibro.autor().get(0);

            //Se instancian las entidades correspondientes
            Autor autor = new Autor(nuevoAutor);
            Libro libro = new Libro(nuevoLibro, autor);

            //Se obtine el nombre del autor para checar si ya existe en la base de datos
            String nombreAutor = nuevoAutor.nombre();

            /*Se busca el ID del autor realizando una consulta a la base de datos con su nombre*/
            try {
                //Si llega a haber algún registro existente del autor, entonces simplemente se asigna ese ID
                libro.setIdAutor(autoresRepository.buscaAutorPorNombre(nombreAutor));
                System.out.println("¡Este autor ya tiene libros registrados!");
            } catch (EmptyResultDataAccessException | AopInvocationException e) { //Si no hay autores con ese nombre, entonces no existe
                //Se hace una consulta del último ID y se asigna el ID que sigue al nuevo autor
                libro.setIdAutor(autoresRepository.buscaSiguienteID());
                //El ID no fue encontrado, entonces se crea un nuevo registro en la tabla de Autores
                autoresRepository.save(autor);
                System.out.println("Aún no hay registro de este autor... ¡Creando uno nuevo!");
            }

            /*Ahora se verifica que el libro no exista ya en la base de datos*/
                //Checamos si existe buscandolo por su título
                String tituloLibroABuscar = libro.getTitulo();

                if (tituloLibroABuscar.equals(librosRepository.buscaLibroPorTitulo(tituloLibroABuscar))){
                    System.out.println(librosRepository.buscaLibroPorTitulo(tituloLibroABuscar));
                    System.out.println("...Sin embargo, este libro ya está registrado :( \n¡Intenta buscar otro Libro!");
                } else { //El libro no existe
                    libro.setIdioma(nuevoLibro.idiomas().get(0)); //Sólo se selecciona un idioma, el primero
                    librosRepository.save(libro);
                    System.out.println("Además, este libro es nuevo. ¡Registro relizado!");
                }

            listaLibroEspecifico(tituloLibroABuscar);

        } else {
            System.out.println("Libro no encontrado");
        }

    }

}
