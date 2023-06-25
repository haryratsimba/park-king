
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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping(value = "/parkings", produces = MediaType.APPLICATION_JSON_VALUE)
public class ParkingController {

    @Autowired
    private ParkingService service;

    // @formatter:off
    @Operation(summary = "Recupere la liste des parking d'une ville correspondants aux criteres")
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "200", description = "Liste des parkings d'une ville", 
        content = { @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = Parking.class))) }),
        @ApiResponse(responseCode = "400", description = "Latitude/longitude/distance fournis mais incomplets", content = @Content)
    })
    @GetMapping("/{cityCode}")
    public ResponseEntity<ParkkingApiResponse<List<Parking>>> getParkings(
            @PathVariable String cityCode,
            @RequestParam("lat") Optional<Double> lat,
            @RequestParam("lng") Optional<Double> lng,
            @RequestParam("distance") Optional<Integer> distance) {
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
