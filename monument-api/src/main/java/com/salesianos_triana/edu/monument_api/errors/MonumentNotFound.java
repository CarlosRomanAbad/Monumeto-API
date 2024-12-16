package com.salesianos_triana.edu.monument_api.errors;

public class MonumentNotFound extends RuntimeException {
    public MonumentNotFound(Long id) {
        super("No hay monumentos con ese ID: %d".formatted(id));
    }

    public  MonumentNotFound (String msg){
        super(msg);
    }

    public MonumentNotFound() {
        super("No se han encontrado monumentos");
    }
}