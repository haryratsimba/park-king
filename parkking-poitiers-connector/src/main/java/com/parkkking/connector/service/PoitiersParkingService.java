
package com.parkkking.connector.service;

import java.util.List;
import java.util.Optional;

import com.parkkking.connector.data.ParkingData;

/**
 * Service permettant de recuperer les donnees parking de la ville de Poitiers.
 * 
 * @author hary.ratsimba
 *
 */
@Service
public class PoitiersParkingService implements CityParkingService {

    @Override
    public List<ParkingData> getParkings(Optional<Double> lat, Optional<Double> lng, Optional<Integer> distance) {
        // TODO Auto-generated method stub
        return null;
    }

}
