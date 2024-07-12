package com.aluradesafio.literalura.principal;

//import com.aluradesafio.literalura.model.Datos;
import com.aluradesafio.literalura.model.*;
import com.aluradesafio.literalura.repository.AutorRepository;
import com.aluradesafio.literalura.repository.LibroRepository;
import com.aluradesafio.literalura.service.ConsumoAPILibro;
import com.aluradesafio.literalura.service.ConvierteDatos;

import java.util.*;
import java.util.stream.Collectors;

public class Principal {
    private Scanner teclado = new Scanner(System.in); //Creamos el teclado para recibir los datos
    private ConsumoAPILibro consumoAPILibro = new ConsumoAPILibro();
    private static final String URL_BASE = "https://gutendex.com/books/";
    private ConvierteDatos convierteDatos = new ConvierteDatos(); //llamamos la clase convierte datos
    private List<DatosLibro> datosLibro = new ArrayList<>();
    private LibroRepository libroRepository;
    private AutorRepository autorRepository;
    private List<Libro> libros;
    private Optional<Libro> libroBuscado;


    public Principal(LibroRepository libroRepository, AutorRepository autorRepository) {
        this.libroRepository = libroRepository;
        this.autorRepository = autorRepository;
    }

    public void muestraElMenu(){
        var opcion = -1;
        while(opcion != 0){
            var menu = """
                    \n**********************************************************************
                    1 - Buscar libro web
                    2 - Buscar libro por titulo
                    3 - Listar libros registrados
                    4 - Listar autores registrados
                    5 - Listar autores vivos en un determinado año
                    6 - Listar libros por idioma
                    
                    0 - Salir
                    """;
            System.out.println(menu);
            opcion = teclado.nextInt();
            teclado.nextLine();

            switch (opcion){
                case 1:
                    buscarLibroWeb();
                    break;
                case 2:
                    buscarLibroPorTitulo();
                    break;
                case 3:
                    listarLibrosRegistrados();
                    break;
                case 0:
                    System.out.println("Saliendo de la aplicación...");
                    break;
            }
        }
    }

    private Datos getDatosLibro(){
        System.out.println("Escribe el nombre del libro que deseas buscar");
        var nombreLibro = teclado.nextLine();
        var json = consumoAPILibro.obtenerDatos(URL_BASE+"?search="+ nombreLibro.replace(" ", "+"));
        Datos datos = convierteDatos.obtenerDatos(json, Datos.class);
        return datos;
    }
    private void buscarLibroWeb(){
        try {
            Datos datos = getDatosLibro();
            DatosLibro datosLibro = datos.resultados().get(0);
            DatosAutor datosAutor = datosLibro.autor().get(0);
            Autor autor = saveAutor(datosAutor);
            saveLibro(datosLibro, autor);
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
            System.out.println("Ya existe en la base de datos.");
        }
    }

    private Autor saveAutor(DatosAutor datosAutor) {
        Autor autor = null;
        try {
            autor = new Autor();
            autor.setNombre(datosAutor.nombre());
            autor.setFechaDeNacimiento(datosAutor.fechaDeNacimiento());
            autor.setFechaDeFallecimiento(datosAutor.fechaDeFallecimiento());
            autorRepository.save(autor);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return autor;
    }

    private Libro saveLibro(DatosLibro datosLibro, Autor autor) {
        Libro libro = null;
        try {
            libro = new Libro();
            libro.setAutor(autor);
            libro.setTitulo(datosLibro.titulo());
            libro.setIdioma(datosLibro.idioma().get(0));
            libro.setDescargas(datosLibro.descargas());
            libroRepository.save(libro);
        } catch(Exception ex) {
            System.out.println(ex.getMessage());
        }
        return libro;
    }

    private void buscarLibroPorTitulo() {
//        System.out.println("Escribe el nombre del libro que deseas buscar");
//        var nombreLibro = teclado.nextLine();
//        var json = consumoAPILibro.obtenerDatos(URL_BASE+"?search="+ nombreLibro.replace(" ", "+"));
//        //para mostrar los datos
//        Datos datos = convierteDatos.obtenerDatos(json, Datos.class);
//        //System.out.println(datos);
//        Optional<DatosLibro> libroBuscado = datos.resultados().stream()
//                .filter(l->l.titulo().toUpperCase().contains(nombreLibro.toUpperCase()))
//                .findFirst();
//        if (libroBuscado.isPresent()) {
//            System.out.println("Libro encontrado ");
//            System.out.println(libroBuscado.get());
//        } else {
//            System.out.println("Libro no encontrado");
//        }

        System.out.println("Escribe el nombre del libro que deseas buscar");
        var nombreLibro = teclado.nextLine();
        //vamos a nuestra LibroRepository
        libroBuscado = libroRepository.findByTituloContainsIgnoreCase(nombreLibro);

        if (libroBuscado.isPresent()){
            System.out.println("El libro buscado es: " +libroBuscado.get());
        } else {
            System.out.println("Libro no encontrado!");
        }
    }

    private void listarLibrosRegistrados() {
//        System.out.println("List registered books\n---------------------");
//        libros = repositorio.findAll();
//        libros.stream()
//                .sorted(Comparator.comparing(Libro::getTitulo))
//                .forEach(System.out::println);
        System.out.println("Lista de libros registrados\n");
        libros = libroRepository.findAll();
        libros.stream()
                .sorted(Comparator.comparing(Libro::getTitulo))
                .forEach(System.out::println);
    }
}
