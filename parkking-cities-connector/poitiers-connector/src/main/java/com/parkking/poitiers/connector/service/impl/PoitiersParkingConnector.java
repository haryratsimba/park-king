
package com.parkking.poitiers.connector.service.impl;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.parkking.connector.cities.constants.Constants;
import com.parkking.connector.cities.data.ParkingDataStandardizable;
import com.parkking.connector.cities.opendata.data.OpenDataRecord;
import com.parkking.connector.cities.service.CityParkingConnector;
import com.parkking.poitiers.opendata.data.PoitiersParkingOpenData;
import com.parkking.poitiers.opendata.data.PoitiersRealTimeParkingOpenData;
import com.parkking.poitiers.opendata.service.PoitiersParkingFetchService;

/**
 * Service permettant de recuperer les donnees parking de la ville de Poitiers.
 * 
 * @author hary.ratsimba
 *
 */
@Service(value = Constants.POITIERS_CONNECTOR)
public class PoitiersParkingConnector implements CityParkingConnector {
    
    @Autowired
    private PoitiersParkingFetchService service;

    @Override
    public Collection<ParkingDataStandardizable> getParkings(Optional<Double> lat, Optional<Double> lng, Optional<Integer> distance) throws InterruptedException, ExecutionException {
        
        // Recupere la liste des parkings ayant des donnees a jour en temps reel
        OpenDataRecord<PoitiersRealTimeParkingOpenData> realtimeParkings = service.getRealTimeParkingData(lat, lng, distance);
        
        // Recupere la liste exhaustive des parking sur Poitiers
        OpenDataRecord<PoitiersParkingOpenData> allParkings = service.getAllParkingsData(lat, lng, distance);
        
        // On se base sur les coordonnees pour determiner si les parking sont les meme entre les 2 endpoints
        // On merge les parking en donnant la priorite a ceux qui ont les donnees en temps reel
        Map<Pair<Double, Double>, ParkingDataStandardizable> parkings = Stream.of(realtimeParkings.getStandardizedParkingsList(), allParkings.getStandardizedParkingsList())
                .flatMap(List::stream)
                .filter(p -> p.getLat() != null && p.getLng() != null)
                .collect(Collectors.toMap(p -> Pair.of(p.getLat(), p.getLng()), Function.identity(), (realTimeP, basicP) -> realTimeP));
        
        return parkings.values();
    }

}