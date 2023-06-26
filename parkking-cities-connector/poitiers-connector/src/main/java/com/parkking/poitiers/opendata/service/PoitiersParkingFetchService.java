
package com.parkking.poitiers.opendata.service;

import java.util.Optional;

import com.parkking.connector.cities.opendata.data.OpenDataRecord;
import com.parkking.poitiers.opendata.data.PoitiersParkingOpenData;
import com.parkking.poitiers.opendata.data.PoitiersRealTimeParkingOpenData;

/**
 * Interface permettant de recuperer les donnees depuis l'API (Open Data) de la metropolle de Poitiers.
 * 
 * @author hary.ratsimba
 *
 */
public interface PoitiersParkingFetchService {
    
    static final String API_ALL_PARKING_URL = "https://data.grandpoitiers.fr/api/records/1.0/search/?dataset=mobilite-parkings-grand-poitiers-donnees-metiers&rows=1000&facet=nom_du_parking&facet=zone_tarifaire&facet=statut2&facet=statut3";

    static final String API_REALTIME_PARKING_URL = "https://data.grandpoitiers.fr/api/records/1.0/search/?dataset=mobilites-stationnement-des-parkings-en-temps-reel&facet=nom";
    
    /**
     * Retourne la liste des parking de Poitiers mise a jour en temps reel.
     * @param lat latitude.
     * @param lng longitude.
     * @param distance en metres.
     * @return la liste des parking de Poitiers mise a jour en temps reel.
     */
    OpenDataRecord<PoitiersRealTimeParkingOpenData> getRealTimeParkingData(Optional<Double> lat, Optional<Double> lng, Optional<Integer> distance);

    /**
     * Retourne la liste exhaustive des parking de Poitiers.
     * @param lat latitude.
     * @param lng longitude.
     * @param distance en metres.
     * @return la liste exhaustive des parking de Poitiers.
     */
    OpenDataRecord<PoitiersParkingOpenData> getAllParkingsData(Optional<Double> lat, Optional<Double> lng, Optional<Integer> distance);

}
