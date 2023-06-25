
package com.parkking.poitiers.opendata.service.impl;

import java.util.Optional;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.parkking.connector.cities.opendata.data.OpenDataRecord;
import com.parkking.connector.cities.opendata.utils.OpenDataUtils;
import com.parkking.poitiers.opendata.data.PoitiersParkingOpenData;
import com.parkking.poitiers.opendata.data.PoitiersRealTimeParkingOpenData;
import com.parkking.poitiers.opendata.service.PoitiersParkingFetchService;

@Service
public class PoitiersParkingFetchServiceImpl implements PoitiersParkingFetchService {

    @Override
    public OpenDataRecord<PoitiersRealTimeParkingOpenData> getRealTimeParkingData(Optional<Double> lat, Optional<Double> lng, Optional<Integer> distance) {
        String apiUrl = OpenDataUtils.appendUrlGeoParams(API_REALTIME_PARKING_URL, lat, lng, distance);

        RestTemplate restTemplate = new RestTemplate();
        OpenDataRecord<PoitiersRealTimeParkingOpenData> response = restTemplate.exchange(apiUrl, HttpMethod.GET, null, new ParameterizedTypeReference<OpenDataRecord<PoitiersRealTimeParkingOpenData>>() {
        }).getBody();
        
        return response;
    }

    @Override
    public OpenDataRecord<PoitiersParkingOpenData> getAllParkingsData(Optional<Double> lat, Optional<Double> lng, Optional<Integer> distance) {
        String apiUrl = OpenDataUtils.appendUrlGeoParams(API_ALL_PARKING_URL, lat, lng, distance);

        RestTemplate restTemplate = new RestTemplate();
        OpenDataRecord<PoitiersParkingOpenData> response = restTemplate.exchange(apiUrl, HttpMethod.GET, null, new ParameterizedTypeReference<OpenDataRecord<PoitiersParkingOpenData>>() {
        }).getBody();
        
        return response;
    }

}
