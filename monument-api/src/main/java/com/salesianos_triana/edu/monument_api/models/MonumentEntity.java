package com.salesianos_triana.edu.monument_api.models;



import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Builder
@NoArgsConstructor
@Data
public class MonumentEntity {
    
  
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String countryCode , countryName , cityName ,   monumentName , monumentDescription , photoUrl;

    private Double lenght , latitude;

}
