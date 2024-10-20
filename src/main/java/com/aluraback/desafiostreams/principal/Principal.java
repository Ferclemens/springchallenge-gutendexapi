package com.aluraback.desafiostreams.principal;

import com.aluraback.desafiostreams.model.Datos;
import com.aluraback.desafiostreams.model.DatosLibros;
import com.aluraback.desafiostreams.services.ConsumoAPI;
import com.aluraback.desafiostreams.services.ConvierteDatos;

import java.util.Comparator;
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

        
    }

}
