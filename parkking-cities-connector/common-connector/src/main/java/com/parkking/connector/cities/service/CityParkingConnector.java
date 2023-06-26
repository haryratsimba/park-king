
package com.parkking.connector.cities.service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

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
     * @throws ExecutionException 
     * @throws InterruptedException 
     */
    public Collection<ParkingDataStandardizable> getParkings(Optional<Double> lat, Optional<Double> lng, Optional<Integer> distance) throws Exception;

}