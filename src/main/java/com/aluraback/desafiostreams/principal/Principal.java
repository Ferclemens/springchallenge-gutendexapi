package com.aluraback.desafiostreams.principal;

import com.aluraback.desafiostreams.model.Datos;
import com.aluraback.desafiostreams.model.DatosLibros;
import com.aluraback.desafiostreams.services.ConsumoAPI;
import com.aluraback.desafiostreams.services.ConvierteDatos;

import java.util.Comparator;
import java.util.DoubleSummaryStatistics;
import java.util.Optional;
import java.util.Scanner;
import java.util.stream.Collectors;

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

        //TOP 20 libros más descargados
        System.out.println("TOP 20 libros más descargados");
        datos.listaDeLibros().stream()
                .sorted(Comparator.comparing(DatosLibros::numeroDeDescargas).reversed())
                .limit(20)
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

        //Estadísticas sobre los libros
        System.out.println("Algunas estadísticas de descargas");
        DoubleSummaryStatistics estadisticas = datosBusqueda.listaDeLibros().stream()
                .filter(l -> l.numeroDeDescargas() > 0)
                .collect(Collectors.summarizingDouble(DatosLibros::numeroDeDescargas));
        System.out.println("cantidad media de descargas: " + estadisticas.getAverage());
        System.out.println("cantidad máxima de descargas: " + estadisticas.getMax());
        System.out.println("cantidad mínima de descargas: " + estadisticas.getMin());
        System.out.println("cantidad de registros evaluados para calcular estadísticas " + estadisticas.getCount());
    }

}
