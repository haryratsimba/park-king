
package com.parkking.connector.poitiers.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.parkking.connector.cities.data.ParkingDataStandardizable;
import com.parkking.connector.cities.data.opendata.OpenDataRecord;
import com.parkking.connector.cities.service.CityParkingConnector;
import com.parkking.connector.poitiers.opendata.PoitiersParkingOpenData;
import com.parkking.connector.poitiers.opendata.PoitiersRealTimeParkingOpenData;
import com.parkking.constants.cities.connector.Constants;

/**
 * Service permettant de recuperer les donnees parking de la ville de Poitiers.
 * 
 * @author hary.ratsimba
 *
 */
@Service(value = Constants.POITIERS_CONNECTOR)
public class PoitiersParkingConnector implements CityParkingConnector {

    private static final String API_ALL_PARKING_URL = "https://data.grandpoitiers.fr/api/records/1.0/search/?dataset=mobilite-parkings-grand-poitiers-donnees-metiers&rows=1000&facet=nom_du_parking&facet=zone_tarifaire&facet=statut2&facet=statut3";

    private static final String API_REALTIME_PARKING_URL = "https://data.grandpoitiers.fr/api/records/1.0/search/?dataset=mobilites-stationnement-des-parkings-en-temps-reel&facet=nom";

    public List<ParkingDataStandardizable> getParkings(Optional<Double> lat, Optional<Double> lng, Optional<Integer> distance) {
        
        StringBuilder urlBuilder = new StringBuilder(API_ALL_PARKING_URL);
        
        if (lat.isPresent() && lng.isPresent() && distance.isPresent()) {
            // Ex: &geofilter.distance=46.58369748714056,0.3324571251869201,50
            urlBuilder.append("&geofilter.distance=");
            urlBuilder.append(String.format("%f,%f,%d", lat.get(), lng.get(), distance.get()));
        }

        RestTemplate restTemplate = new RestTemplate();
        
        // TODO: on pourrait envisager de paralleliser l'envoi des 2 requetes
        
        // Recupere la liste exhaustive des parking sur Poitiers
        String allParkingsUrl = this.buildUrl(API_ALL_PARKING_URL, lat, lng, distance);
        OpenDataRecord<PoitiersParkingOpenData> allParkingsResponse = restTemplate.exchange(allParkingsUrl, HttpMethod.GET, null, new ParameterizedTypeReference<OpenDataRecord<PoitiersParkingOpenData>>() {}).getBody();

        // Recupere la liste des parkings ayant des donnees a jour en temps reel
        String realtimeParkingsUrl = this.buildUrl(API_REALTIME_PARKING_URL, lat, lng, distance);
        OpenDataRecord<PoitiersRealTimeParkingOpenData> realTimeParkingResponse = restTemplate.exchange(realtimeParkingsUrl, HttpMethod.GET, null, new ParameterizedTypeReference<OpenDataRecord<PoitiersRealTimeParkingOpenData>>() {}).getBody();
        
        // On se base sur les coordonnees pour determiner si les parking sont les meme entre les 2 endpoints
        // On merge les parking en donnant la priorite a ceux qui ont les donnees en temps reel
        
        // TODO
        return new ArrayList<>();
    }

}