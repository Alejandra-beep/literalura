package com.aluradesafio.literalura.principal;

//import com.aluradesafio.literalura.model.Datos;
import com.aluradesafio.literalura.model.Datos;
import com.aluradesafio.literalura.model.DatosLibro;
import com.aluradesafio.literalura.model.Libro;
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
    private LibroRepository repositorio;
    private List<Libro> libros;
    private Optional<Libro> libroBuscado;


    public Principal(LibroRepository repository) {
        this.repositorio = repository;
    }

//    public Principal() {
//
//    }

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
        System.out.println(json);
        Datos datos = convierteDatos.obtenerDatos(json, Datos.class);
        return datos;
    }
    private void buscarLibroWeb(){
        Datos datos = getDatosLibro();
        datos.resultados().stream().forEach(datosLibro -> {
            Libro libro = new Libro(datosLibro.titulo(), "", "", datosLibro.descargas());
            repositorio.save(libro);
        });

        System.out.println(datos);
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
        libroBuscado = repositorio.findByTituloContainsIgnoreCase(nombreLibro);

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
        libros = repositorio.findAll();
        libros.stream()
                .sorted(Comparator.comparing(Libro::getTitulo))
                .forEach(System.out::println);
    }
}
