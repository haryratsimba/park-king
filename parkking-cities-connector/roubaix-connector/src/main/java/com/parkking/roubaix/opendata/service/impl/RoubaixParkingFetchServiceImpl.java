
package com.parkking.roubaix.opendata.service.impl;

import java.util.Optional;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.parkking.connector.cities.opendata.data.OpenDataRecord;
import com.parkking.connector.cities.opendata.utils.OpenDataUtils;
import com.parkking.roubaix.opendata.data.RoubaixParkingOpenData;
import com.parkking.roubaix.opendata.service.RoubaixParkingFetchService;

@Service
public class RoubaixParkingFetchServiceImpl implements RoubaixParkingFetchService {

    @Override
    public OpenDataRecord<RoubaixParkingOpenData> getAllParkingsData(Optional<Double> lat, Optional<Double> lng, Optional<Integer> distance) {
        String apiUrl = OpenDataUtils.appendUrlGeoParams(API_ALL_PARKING_URL, lat, lng, distance);

        RestTemplate restTemplate = new RestTemplate();
        OpenDataRecord<RoubaixParkingOpenData> response = restTemplate.exchange(apiUrl, HttpMethod.GET, null, new ParameterizedTypeReference<OpenDataRecord<RoubaixParkingOpenData>>() {
        }).getBody();
        
        return response;
    }

}
