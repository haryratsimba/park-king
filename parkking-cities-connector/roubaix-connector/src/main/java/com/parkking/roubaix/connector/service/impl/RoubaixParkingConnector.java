
package com.parkking.roubaix.connector.service.impl;

import java.util.Collection;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.parkking.connector.cities.constants.Constants;
import com.parkking.connector.cities.data.ParkingDataStandardizable;
import com.parkking.connector.cities.opendata.data.OpenDataRecord;
import com.parkking.connector.cities.service.CityParkingConnector;
import com.parkking.roubaix.opendata.data.RoubaixParkingOpenData;
import com.parkking.roubaix.opendata.service.RoubaixParkingFetchService;

/**
 * Service permettant de recuperer les donnees parking de la ville de Roubaix dans un format standardise pour toutes les villes.
 * 
 * @author hary.ratsimba
 *
 */
@Service(value = Constants.ROUBAIX_CONNECTOR)
public class RoubaixParkingConnector implements CityParkingConnector {
    
    @Autowired
    private RoubaixParkingFetchService service;

    @Override
    public Collection<ParkingDataStandardizable> getParkings(Optional<Double> lat, Optional<Double> lng, Optional<Integer> distance) throws InterruptedException, ExecutionException {
        
        // Recupere la liste exhaustive des parking sur Roubaix
        OpenDataRecord<RoubaixParkingOpenData> allParkings = service.getAllParkingsData(lat, lng, distance);
        
        return allParkings.getStandardizedParkingsList();
    }

}