
package com.parkking.service;

import java.util.List;
import java.util.Optional;

import com.parkking.dto.Parking;

/**
 * Service des donnees de parking.
 * 
 * @author hary.ratsimba
 *
 */
public interface ParkingService {

    /**
     * Retourne la {@link List} des {@link Parking} d'une ville, situes dans le perimetre (en metres) des coordonees en parametre.
     * Si les coordonnees et la distance ne sont pas fournis, on retourne tous les parking de la ville.
     * 
     * @param cityCode ville sur laquelle la recherche s'effectue.
     * @param lat latitude.
     * @param lng longitude.
     * @param distance en metres.
     * @return la {@link List} des {@link Parking} d'une ville, situes dans le perimetre (en metres) des coordonees en parametre.
     */
    public List<Parking> getParkings(String cityCode, Optional<Double> lat, Optional<Double> lng, Optional<Integer> distance);

}
