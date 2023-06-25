
package com.parkking.connector.poitiers.opendata;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.parkking.connector.cities.data.ParkingDataStandardizable;

import lombok.Data;

@Data
public class PoitiersParkingOpenData implements ParkingDataStandardizable {

    @JsonProperty(value = "nom_du_par")
    private String nomDuPar;

    @JsonProperty(value = "nb_places")
    private int nbPlaces;

    @JsonProperty(value = "geo_point_2d")
    private List<Double> geoPoint2d;

    @Override
    public String getName() {
        return nomDuPar;
    }

    @Override
    public int getCapacity() {
        return nbPlaces;
    }

    @Override
    public Integer getRemaining() {
        return null;
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
