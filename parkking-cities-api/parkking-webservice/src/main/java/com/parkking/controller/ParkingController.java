
package com.parkking.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.parkking.api.ParkkingApiResponse;
import com.parkking.dto.Parking;
import com.parkking.exception.ParkkingApiException;
import com.parkking.service.ParkingService;

@RestController
@RequestMapping(value = "/parkings", produces = MediaType.APPLICATION_JSON_VALUE)
public class ParkingController {

    @Autowired
    private ParkingService service;

    @GetMapping("/{cityCode}")
    public ResponseEntity<ParkkingApiResponse<List<Parking>>> getParkings(
    // @formatter:off
            @PathVariable String cityCode,
            @RequestParam("lat") Optional<Double> lat,
            @RequestParam("lng") Optional<Double> lng,
            @RequestParam("lng") Optional<Integer> distance) {
        // @formatter:on

        // Lat/lng sont optionnels, mais si l'un est renseigne, l'autre doit l'etre aussi
        if (lat.isEmpty() && lng.isPresent() || lat.isPresent() && lng.isEmpty()) {
            throw new ParkkingApiException("Cannot specify only one part of the coordinates, if one is specified the other one is mandatory", 400);
        }

        // La distance doit etre specifiee avec lat/lng
        if (lat.isPresent() && distance.isEmpty()) {
            throw new ParkkingApiException("Cannot specify the coordinates without the distance", 400);
        }

        List<Parking> parkings = service.getParkings(cityCode, lat, lng, distance);

        ParkkingApiResponse<List<Parking>> response = new ParkkingApiResponse<>();
        response.setData(parkings);

        return ResponseEntity.ok(response);
    }

}
