
package com.parkking.poitiers.opendata.data;

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

    private String dist;

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
    public Double getLat() {
        return geoPoint2d != null && geoPoint2d.size() == 2 ? geoPoint2d.get(0) : null;
    }

    @Override
    public Double getLng() {
        return geoPoint2d != null && geoPoint2d.size() == 2 ? geoPoint2d.get(1) : null;
    }

    @Override
    public String getDistance() {
        return dist;
    }

}
