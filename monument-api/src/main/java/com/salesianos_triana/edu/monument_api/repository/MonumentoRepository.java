package com.salesianos_triana.edu.monument_api.repository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

import com.salesianos_triana.edu.monument_api.models.MonumentEntity;

import jakarta.annotation.PostConstruct;

@Repository
public class MonumentoRepository {
    
    private HashMap<Long, MonumentEntity> monumentos = new HashMap<>();

    @PostConstruct
    public void initMonuments(){
        
        addMonument(MonumentEntity.builder()
            .id(1l)
            .cityName("Sevilla")
            .countryCode("ES")
            .countryName("Spain")
            .lenght(10.0)
            .latitude(37.382830)
            .monumentName("Giralda")
            .monumentDescription("The Giralda is the bell tower of the Seville Cathedral in Seville, Spain. It was originally built as the minaret for the Great Mosque of Seville in Al-Andalus, Moorish Spain, during the reign of the Almohad dynasty, with a Renaissance-style top subsequently added by the Catholics after the expulsion of the Muslims from the area.")
            .photoUrl("https://upload.wikimedia.org/wikipedia/commons/thumb/8/8d/Giralda%2C_Sevilla%2C_2015-12-09%2C_JD_01.jpg/800px-Giralda%2C_Sevilla%2C_2015-12-09%2C_JD_01.jpg")
            .build());

        addMonument(MonumentEntity.builder()
            .id(2l)
            .cityName("Sevilla")
            .countryCode("ES")
            .countryName("Spain")
            .lenght(10.0)
            .latitude(37.3833)
            .monumentName("Plaza de España")
            .monumentDescription("The Plaza de España is a plaza in the Parque de María Luisa, in Seville, Spain, built in 1928 for the Ibero-American Exposition of 1929. It is a landmark example of the Regionalism Architecture, mixing elements of the Renaissance Revival and Moorish Revival styles of Spanish architecture.")
            .photoUrl("https://upload.wikimedia.org/wikipedia/commons/thumb/8/8d/Giralda%2C_Sevilla%2C_2015-12-09%2C_JD_01.jpg/800px-Giralda%2C_Sevilla%2C_2015-12-09%2C_JD_01.jpg")
            .build());


        addMonument(MonumentEntity.builder()
            .id(3l)
            .cityName("Úbeda")
            .countryCode("ES")
            .countryName("Spain")
            .lenght(10.0)
            .latitude(38.0119)
            .monumentName("Plaza Vázquez de Molina")
            .monumentDescription("The Plaza Vázquez de Molina is a square located in the city of Úbeda, in the province of Jaén, Spain. It was declared a World Heritage Site by UNESCO in 2003, along with the rest of the city.")
            .photoUrl("https://upload.wikimedia.org/wikipedia/commons/thumb/8/8d/Giralda%2C_Sevilla%2C_2015-12-09%2C_JD_01.jpg/800px-Giralda%2C_Sevilla%2C_2015-12-09%2C_JD_01.jpg")
            .build());

        addMonument(MonumentEntity.builder()
        .id(4L)
            .cityName("Úbeda")
            .countryCode("ES")
            .countryName("Spain")
            .lenght(10.0)
            .latitude(38.0119)
            .monumentName("Sacra Capilla del Salvador")
            .monumentDescription("The Sacra Capilla del Salvador is a chapel located in the city of Úbeda, in the province of Jaén, Spain. It was declared a World Heritage Site by UNESCO in 2003, along with the rest of the city.")
            .photoUrl("https://upload.wikimedia.org/wikipedia/commons/thumb/8/8d/Giralda%2C_Sevilla%2C_2015-12-09%2C_JD_01.jpg/800px-Giralda%2C_Sevilla%2C_2015-12-09%2C_JD_01.jpg")
            .build());
    }

    public List<MonumentEntity> getAllMonuments(){
        return monumentos.values().stream().toList();
    }

    public MonumentEntity addMonument(MonumentEntity monument){
        monumentos.put(monument.getId(), monument);
        return monument;
    }

    public Optional<MonumentEntity> getMonumentById(Long id){
        return Optional.ofNullable(monumentos.get(id));
    }

    public void deleteMonumentById(Long id){
        monumentos.remove(id);
    }

    public Optional<MonumentEntity> editMonument(Long id, MonumentEntity monument){
      return Optional.ofNullable(monumentos.computeIfPresent(id, (k,v) -> {
        v.setCityName(monument.getCityName());
        v.setCountryCode(monument.getCountryCode());
        v.setCountryName(monument.getCountryName());
        v.setLenght(monument.getLenght());
        v.setLatitude(monument.getLatitude());
        v.setMonumentName(monument.getMonumentName());
        v.setMonumentDescription(monument.getMonumentDescription());
        v.setPhotoUrl(monument.getPhotoUrl());
        return v;
      }));
    }


    public List<MonumentEntity> filterAndOrder(double maxLatitude, String sortDirection) {
        List<MonumentEntity> data = new ArrayList<>(monumentos.values());
        List<MonumentEntity> result = new ArrayList<>();

        if (maxLatitude < 0) {
            result = data;
        } else {
            result = data
                    .stream()
                    .filter(m -> m.getLatitude() <= maxLatitude)
                    .collect(Collectors.toCollection(ArrayList::new));
        }

        if (sortDirection.equalsIgnoreCase("asc"))
            result.sort(Comparator.comparing(MonumentEntity::getMonumentName));
        else if (sortDirection.equalsIgnoreCase("desc"))
            result.sort(Comparator.comparing(MonumentEntity::getMonumentName).reversed());

        return Collections.unmodifiableList(result);
    }
}
