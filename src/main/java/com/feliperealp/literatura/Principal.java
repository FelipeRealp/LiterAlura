package com.feliperealp.literatura;

import com.feliperealp.literatura.autor.Autor;
import com.feliperealp.literatura.autor.AutorRepository;
import com.feliperealp.literatura.autor.DatosAutor;
import com.feliperealp.literatura.libro.DatosLibro;
import com.feliperealp.literatura.libro.Libro;
import com.feliperealp.literatura.libro.LibroRepository;
import com.feliperealp.literatura.service.ConsumoAPI;
import com.feliperealp.literatura.service.ConversorDatos;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;


public class Principal {

    private LibroRepository libroRepositorio;
    private AutorRepository autorRepositorio;

    Scanner teclado = new Scanner(System.in);
    private String menuDeOpciones = """
            
            Elija la opcion a traves de su numero:
            1. Buscar libro por titulo
            2. Listar libros registrados
            3. Listar autores registrados
            4. Listar autores vivos en un determinado año
            5. Listar libros por idioma
            0. Salir
            
            """;

    ConsumoAPI consumo = new ConsumoAPI();
    ConversorDatos conversor = new ConversorDatos();
    private String url = "https://gutendex.com/books";

    public Principal(LibroRepository libroRepo, AutorRepository autorRepo) {
        this.libroRepositorio = libroRepo;
        this.autorRepositorio = autorRepo;
    }

    public void mostrarOpciones() {

        var opcion = -1;
        while (opcion != 0) {

            System.out.println(menuDeOpciones);

            try {
                opcion = Integer.parseInt(teclado.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Ingresa una opcion valida");
                continue;
            }

            switch (opcion) {
                case 1:
                    buscarSeriePorTitulo();
                    break;
                case 2:
                    listarLibrosRegistrados();
                    break;
                case 3:
                    listarAutoresRegistrados();
                    break;
                case 4:
                    listarAutoresVivos();
                    break;
                case 5:
                    listarLibroPorIdioma();
                    break;
                case 0:
                    System.out.println("Cerrando la aplicacion");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Ingresa una opcion valida");
                    break;
            }

        }
    }

    public void buscarSeriePorTitulo() {

        System.out.println("Escribe el titulo del libro que deseas buscar");
        var nombre = teclado.nextLine().replace(" ", "+");
        var json = consumo.obtenerDatos(url + "/?search=" + nombre);

        if (nombre.isBlank()) {
            System.out.println("Ingresa un nombre valido");
            return;
        }

        DatosRespuesta datos = conversor.convertirDatos(json, DatosRespuesta.class);

        if (datos.results() == null || datos.results().isEmpty()) {
            System.out.println("Lo sentimos, no se encontro ningun libro con ese titulo");
            return;
        }

        DatosLibro primerLibro = datos.results().get(0);

        if (libroRepositorio.findById(primerLibro.id()).isPresent()) {
            System.out.println("El libro: '" + primerLibro.titulo() + "' ya esta registrado");
            return;
        }
        DatosAutor datosAutor = primerLibro.autores().get(0);

        Optional<Autor> autorExistente = autorRepositorio.findByNombreIgnoreCase(datosAutor.nombre());

        Autor autor;
        if (autorExistente.isPresent()) {
            autor = autorExistente.get();
        } else {
            autor = new Autor(datosAutor);
            autorRepositorio.save(autor);
        }

        Libro libro = new Libro(primerLibro);
        libro.setAutor(autor);

        libroRepositorio.save(libro);
        System.out.println("Libro registrado con exito: " + libro.getTitulo());
    }

    private void listarLibrosRegistrados() {
        List<Libro> libros = libroRepositorio.findAll();
        libros.forEach(System.out::println);
    }

    public void listarAutoresRegistrados() {
        List<Autor> autores = autorRepositorio.findAll();
        autores.forEach(System.out::println);
    }

    public void listarAutoresVivos() {
        System.out.println("Digita el año que quieres consultar");
        var anio = -1;

        try {
            anio = Integer.parseInt(teclado.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("NO has ingresado una opcion valida" + e);
        }

        List<Autor> autores = autorRepositorio.buscarAutoresVivosEnDeterminadoAnio(anio);

        if (autores.isEmpty()) {
            System.out.println("No hay registros de Autores vivos para el año: " + anio);
        } else {
            autores.forEach(System.out::println);
        }

    }

    public void listarLibroPorIdioma() {
        List<String> idiomas = libroRepositorio.buscarIdiomasPresentes();

        if(idiomas.isEmpty()){
            System.out.println("No hay libros registrados");
            return;
        }

        for (int i = 0; i < idiomas.size(); i++) {
            System.out.println((i + 1) + ". " + idiomas.get(i).toUpperCase());
        }

        try{
            var opcion = teclado.nextInt();
            teclado.nextLine();

            if (opcion > 0 && opcion <= idiomas.size()){
                String codigoIdioma = idiomas.get(opcion-1);
                List<Libro> librosFiltrados = libroRepositorio.findByIdioma(codigoIdioma);

                System.out.println("\n Libros en: " + codigoIdioma.toUpperCase());
                librosFiltrados.forEach(System.out::println);
            }
            else {
                System.out.println("Escogiste una opcion no valida");
            }
        } catch (InputMismatchException e){
            System.out.println("Error: debes ingresar un NUMERO");
            teclado.nextLine();
        }
    }
}

