
package com.parkking.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.parkking.api.ParkkingApiResponse;
import com.parkking.dto.City;
import com.parkking.exception.ParkkingApiException;
import com.parkking.service.CityService;

@RestController
@RequestMapping(value = "/cities", produces = MediaType.APPLICATION_JSON_VALUE)
public class CityController {

    @Autowired
    private CityService service;

    @GetMapping
    public ResponseEntity<ParkkingApiResponse<List<City>>> getCities(
            @RequestParam("lat") Optional<Double> lat,
            @RequestParam("lng") Optional<Double> lng) {
        
        // Lat/lng sont optionnels, mais si l'un est renseigne, l'autre doit l'etre aussi
        if (lat.isEmpty() && lng.isPresent() || lat.isPresent() && lng.isEmpty()) {
            throw new ParkkingApiException("Cannot specify only one part of the coordinates, if one is specified the other one is mandatory", 400);
        }

        List<City> cities = new ArrayList<>();
        if (lat.isPresent()) {
            cities = service.getCoordsBelongingCities(lat.get(), lng.get());
        } else {
            cities = service.getAllCities();
        }

        ParkkingApiResponse<List<City>> response = new ParkkingApiResponse<>();
        response.setData(cities);

        return ResponseEntity.ok(response);
    }
    
    @GetMapping("/{code}")
    public ResponseEntity<ParkkingApiResponse<City>> getCityByCode(String code) {
        City city = service.getCityByCode(code);

        ParkkingApiResponse<City> response = new ParkkingApiResponse<>();
        response.setData(city);

        return ResponseEntity.ok(response);
    }

}
