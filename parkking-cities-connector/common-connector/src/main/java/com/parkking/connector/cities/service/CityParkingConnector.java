
package com.parkking.connector.cities.service;

import java.util.List;
import java.util.Optional;

import com.parkking.connector.cities.data.ParkingDataStandardizable;

/**
 * Service permettant de recuperer les donnees parking d'une ville.
 * 
 * @author hary.ratsimba
 *
 */
public interface CityParkingConnector {

    /**
     * Retourne la {@link List} des {@link ParkingDataStandardizable} d'une ville, situes dans le perimetre (en metres) des coordonees en parametre.
     * Si les coordonnees et la distance ne sont pas fournis, on retourne tous les parking de la ville.
     * 
     * @param lat latitude.
     * @param lng longitude.
     * @param distance en metres.
     * @return la {@link List} des {@link Parking} d'une ville, situes dans le perimetre (en metres) des coordonees en parametre.
     */
    public List<ParkingDataStandardizable> getParkings(Optional<Double> lat, Optional<Double> lng, Optional<Integer> distance);

    default String buildUrl(String uri, Optional<Double> lat, Optional<Double> lng, Optional<Integer> distance) {
        StringBuilder urlBuilder = new StringBuilder(uri);

        if (lat.isPresent() && lng.isPresent() && distance.isPresent()) {
            // Ex: &geofilter.distance=46.58369748714056,0.3324571251869201,50
            urlBuilder.append("&geofilter.distance=");
            urlBuilder.append(String.format("%f,%f,%d", lat.get(), lng.get(), distance.get()));
        }

        return urlBuilder.toString();
    }

}