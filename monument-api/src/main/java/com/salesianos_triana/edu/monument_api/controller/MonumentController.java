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



@RestController
@RequiredArgsConstructor
@RequestMapping("/monument")
public class MonumentController {
    
    private final MonumentoRepository monumentoRepository;
    

    @GetMapping("/all")
    public ResponseEntity<List<MonumentEntity>> getAllMonuments() {

        if(monumentoRepository.getAllMonuments().isEmpty())
            return ResponseEntity.notFound().build();


        return ResponseEntity.ok(monumentoRepository.getAllMonuments());
    }

    @GetMapping("/{id}")
    public ResponseEntity<MonumentEntity> getById(@PathVariable Long id) {
        
        MonumentEntity monumentoEncontrado = monumentoRepository.getMonumentById(id).orElse(null);

        return monumentoEncontrado != null ? ResponseEntity.ok(monumentoEncontrado) : ResponseEntity.status(HttpStatus.NOT_FOUND).build();

    }



    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        monumentoRepository.deleteMonumentById(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<MonumentEntity> editMonument(@RequestBody MonumentEntity monumentoEditado, @PathVariable Long id){
        
        return ResponseEntity.of(monumentoRepository.editMonument(id, monumentoEditado));
    }


    @PostMapping
    public ResponseEntity<MonumentEntity> addMonument(@RequestBody MonumentEntity monument){
        return ResponseEntity.status(HttpStatus.CREATED).body(monumentoRepository.addMonument(monument));
    }

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
