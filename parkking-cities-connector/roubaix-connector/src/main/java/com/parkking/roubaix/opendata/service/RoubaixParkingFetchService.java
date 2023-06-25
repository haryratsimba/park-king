
package com.parkking.roubaix.opendata.service;

import java.util.Optional;

import com.parkking.connector.cities.opendata.data.OpenDataRecord;
import com.parkking.roubaix.opendata.data.RoubaixParkingOpenData;

/**
 * Interface permettant de recuperer les donnees depuis l'API (Open Data) de la ville de Roubaix.
 * 
 * @author hary.ratsimba
 *
 */
public interface RoubaixParkingFetchService {
    
    static final String API_ALL_PARKING_URL = "https://opendata.lillemetropole.fr/api/records/1.0/search/?dataset=parkings-et-coordonnees&rows=1000&q=&facet=type&facet=nb_place_h";

    /**
     * Retourne la liste exhaustive des parking de Roubaix.
     * @param lat latitude.
     * @param lng longitude.
     * @param distance en metres.
     * @return la liste exhaustive des parking de Roubaix.
     */
    OpenDataRecord<RoubaixParkingOpenData> getAllParkingsData(Optional<Double> lat, Optional<Double> lng, Optional<Integer> distance);

}
