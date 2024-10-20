package com.aluraback.desafiostreams.principal;

import com.aluraback.desafiostreams.model.Datos;
import com.aluraback.desafiostreams.services.ConsumoAPI;
import com.aluraback.desafiostreams.services.ConvierteDatos;

public class Principal {
    private ConsumoAPI peticionApi = new ConsumoAPI();
    private ConvierteDatos convertir = new ConvierteDatos();
    private static final String URL_BASE = "https://gutendex.com/books/";
    public void muestraMenu(){

        var json = peticionApi.obtenerDatos(URL_BASE);
        //System.out.println(json);
        var datos = convertir.obtenerDatos(json, Datos.class);
        System.out.println(datos);
    }
}
