
package com.parkking.connector.poitiers.opendata;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.parkking.connector.cities.data.ParkingDataStandardizable;

import lombok.Data;

@Data
public class PoitiersRealTimeParkingOpenData implements ParkingDataStandardizable {

    @JsonProperty(value = "nom")
    private String nom;

    @JsonProperty(value = "capacite")
    private double capacite;

    @JsonProperty(value = "places")
    private double places;

    @JsonProperty(value = "geo_point_2d")
    private List<Double> geoPoint2d;

    @Override
    public String getName() {
        return nom;
    }

    @Override
    public int getCapacity() {
        return (int) capacite;
    }

    @Override
    public Integer getRemaining() {
        return (int) places;
    }

    @Override
    public double getLat() {
        return geoPoint2d != null && geoPoint2d.size() == 2 ? geoPoint2d.get(0) : 0d;
    }

    @Override
    public double getLng() {
        return geoPoint2d != null && geoPoint2d.size() == 2 ? geoPoint2d.get(1) : 0d;
    }

}
