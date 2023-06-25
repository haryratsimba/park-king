
package com.parkking.service.impl;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.parkking.connector.cities.data.ParkingDataStandardizable;
import com.parkking.connector.cities.service.CityParkingConnector;
import com.parkking.dto.City;
import com.parkking.dto.Parking;
import com.parkking.exception.ParkkingApiException;
import com.parkking.service.CityService;
import com.parkking.service.ParkingService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ParkingServiceImpl implements ParkingService {

    @Autowired
    private CityService cityService;

    @Autowired
    BeanFactory beans;

    @Override
    public List<Parking> getParkings(String cityCode, Optional<Double> lat, Optional<Double> lng, Optional<Integer> distance) {

        City city = cityService.getCityByCode(cityCode);
        if (city == null) {
            throw new ParkkingApiException("The provided city code is invalid", 400);
        }

        if (city.getConnector() == null) {
            throw new ParkkingApiException("This city is disabled", 500);
        }

        // Charge le connecteur de la ville a partir de son nom
        CityParkingConnector cityParkingConnector = beans.getBean(city.getConnector(), CityParkingConnector.class);

        try {
            Collection<ParkingDataStandardizable> parkings = cityParkingConnector.getParkings(lat, lng, distance);

            return parkings.stream().map(Parking::fromConnectorData).collect(Collectors.toList());

        } catch (Exception e) {
            log.error("Error with {} connector : {}", city.getConnector(), e);

            throw new ParkkingApiException("Could not fetch parking data, please try later", 500);
        }
    }

}
