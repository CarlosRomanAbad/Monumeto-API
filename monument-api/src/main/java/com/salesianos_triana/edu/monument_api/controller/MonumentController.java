package com.salesianos_triana.edu.monument_api.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.salesianos_triana.edu.monument_api.models.MonumentEntity;
import com.salesianos_triana.edu.monument_api.repository.MonumentoRepository;

import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;



@RestController
@RequiredArgsConstructor
@RequestMapping("/monument")
@Tag(name = "Monumento", description = "Monument Controller")
public class MonumentController {
    
    private final MonumentoRepository monumentoRepository;

    @Operation(summary = "Obtiene todos los monumentos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Monumentos obtenidos correctamente",
                    content = @Content(mediaType = "application/json",
                            examples = @ExampleObject(
                                    value = """
                         [
                             {
                                 "id": 1,
                                 "cityName": "Sevilla",
                                 "countryCode": "ES",
                                 "countryName": "Spain",
                                 "latitude": 37.382830,
                                 "monumentName": "Giralda",
                                 "monumentDescription": "Bell tower of the Seville Cathedral.",
                                 "photoUrl": "https://example.com/image.jpg"
                             }
                         ]
                     """)))
    })
    @GetMapping("/all")
    public ResponseEntity<List<MonumentEntity>> getAllMonuments() {
        return ResponseEntity.ok(monumentoRepository.getAllMonuments());
    }



    @Operation(summary = "Obtiene un monumento por su id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Monumento obtenido correctamente",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = MonumentEntity.class),
                            examples = @ExampleObject(
                                    value = """
                         {
                             "id": 1,
                             "cityName": "Sevilla",
                             "countryCode": "ES",
                             "countryName": "Spain",
                             "latitude": 37.382830,
                             "monumentName": "Giralda",
                             "monumentDescription": "Bell tower of the Seville Cathedral.",
                             "photoUrl": "https://example.com/image.jpg"
                         }
                     """))),
            @ApiResponse(responseCode = "404", description = "Monumento no encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<MonumentEntity> getById(@PathVariable Long id) {

        MonumentEntity monumentoEncontrado = monumentoRepository.getMonumentById(id).orElse(null);

        return ResponseEntity.ok(monumentoEncontrado);

    }


    @Operation(summary = "Elimina un monumento por su id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Monumento eliminado correctamente"),
            @ApiResponse(responseCode = "404", description = "Monumento no encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        monumentoRepository.deleteMonumentById(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Edita un monumento por su id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Monumento editado correctamente",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = MonumentEntity.class),
                            examples = @ExampleObject(
                                    value = """
                         {
                             "id": 1,
                             "cityName": "Sevilla",
                             "countryCode": "ES",
                             "countryName": "Spain",
                             "latitude": 37.382830,
                             "monumentName": "Giralda",
                             "monumentDescription": "Bell tower of the Seville Cathedral.",
                             "photoUrl": "https://example.com/image.jpg"
                         }
                     """))),
            @ApiResponse(responseCode = "404", description = "Monumento no encontrado")
    })
    @PutMapping("/{id}")
    public ResponseEntity<MonumentEntity> editMonument(@RequestBody MonumentEntity monumentoEditado, @PathVariable Long id){
        
        return ResponseEntity.of(monumentoRepository.editMonument(id, monumentoEditado));
    }

    @Operation(summary = "Añade un monumento")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Monumento añadido correctamente",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = MonumentEntity.class),
                            examples = @ExampleObject(
                                    value = """
                         {
                             "id": 1,
                             "cityName": "Sevilla",
                             "countryCode": "ES",
                             "countryName": "Spain",
                             "latitude": 37.382830,
                             "monumentName": "Giralda",
                             "monumentDescription": "Bell tower of the Seville Cathedral.",
                             "photoUrl": "https://example.com/image.jpg"
                         }
                     """)))
    })
    @PostMapping
    public ResponseEntity<MonumentEntity> addMonument(@RequestBody MonumentEntity monument){
        return ResponseEntity.status(HttpStatus.CREATED).body(monumentoRepository.addMonument(monument));
    }

    @Operation(summary = "Obtiene los monumentos filtrados y ordenados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Monumentos obtenidos correctamente",
                    content = @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = MonumentEntity.class)),
                            examples = @ExampleObject(
                                    value = """
                         [
                             {
                                 "id": 1,
                                 "cityName": "Sevilla",
                                 "countryCode": "ES",
                                 "countryName": "Spain",
                                 "latitude": 37.382830,
                                 "monumentName": "Giralda",
                                 "monumentDescription": "Bell tower of the Seville Cathedral.",
                                 "photoUrl": "https://example.com/image.jpg"
                             }
                         ]
                     """))),
            @ApiResponse(responseCode = "204", description = "No se han encontrado monumentos")
    })
    @GetMapping
    public ResponseEntity<List<MonumentEntity>> getQuery(
            @RequestParam(required = false, value = "maxLatitude", defaultValue = "max") Double latitude,
            @RequestParam(required = false, value = "sort", defaultValue = "no") String sort) {
            List<MonumentEntity> result = monumentoRepository.filterAndOrder(latitude, sort);

        if (result.isEmpty())
            return ResponseEntity.noContent().build();

        return ResponseEntity.ok(result);
    }


}
