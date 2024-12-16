package com.salesianos_triana.edu.monument_api.errors;

import java.net.URI;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalErrorController {

     @ExceptionHandler(MonumentNotFound.class)
    public ProblemDetail handleProductNotFound(MonumentNotFound ex) {

        ProblemDetail result = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, ex.getMessage());
        result.setTitle("Monumento no encontrado");
        result.setType(URI.create("https://www.salesianos-triana/errors/product-not-found"));
        result.setProperty("author", "Carlitos Román");
        return result;

    }
    
}
