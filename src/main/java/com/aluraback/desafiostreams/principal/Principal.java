package com.aluraback.desafiostreams.principal;

import com.aluraback.desafiostreams.model.Datos;
import com.aluraback.desafiostreams.model.DatosLibros;
import com.aluraback.desafiostreams.services.ConsumoAPI;
import com.aluraback.desafiostreams.services.ConvierteDatos;

import java.util.Comparator;
import java.util.Optional;
import java.util.Scanner;

public class Principal {
    private ConsumoAPI peticionApi = new ConsumoAPI();
    private ConvierteDatos convertir = new ConvierteDatos();
    private Scanner busqueda = new Scanner(System.in);
    private static final String URL_BASE = "https://gutendex.com/books/";
    public void muestraMenu(){

        var json = peticionApi.obtenerDatos(URL_BASE);
        //System.out.println(json);
        var datos = convertir.obtenerDatos(json, Datos.class);
        //System.out.println(datos);

        //TOP 10 libros más descargados
        System.out.println("TOP 10 libros más descargados");
        datos.listaDeLibros().stream()
                .sorted(Comparator.comparing(DatosLibros::numeroDeDescargas).reversed())
                .limit(10)
                .map(l -> l.titulo().toUpperCase())
                .forEach(System.out::println);

        //Busque da de libros por nombre
        System.out.println("Ingrese el nombre del libro que desea buscar: ");
        var tituloABuscar = busqueda.nextLine();
        json = peticionApi.obtenerDatos(URL_BASE + "?search=" + tituloABuscar.replace(" ", "+"));
        var datosBusqueda = convertir.obtenerDatos(json, Datos.class);
        Optional<DatosLibros> libroBuscado = datosBusqueda.listaDeLibros().stream()
                .filter(l -> l.titulo().toUpperCase().contains(tituloABuscar.toUpperCase()))
                .findFirst();
        if(libroBuscado.isPresent()){
            System.out.println("Libro encontrado");
            System.out.println(libroBuscado.get());
        } else {
            System.out.println("Libro no encontrado");
        }
    }

}
