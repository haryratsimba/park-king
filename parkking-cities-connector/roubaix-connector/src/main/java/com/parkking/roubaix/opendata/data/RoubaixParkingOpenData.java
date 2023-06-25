
package com.parkking.roubaix.opendata.data;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.parkking.connector.cities.data.ParkingDataStandardizable;
import com.parkking.connector.cities.opendata.data.OpenDataField;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class RoubaixParkingOpenData extends OpenDataField implements ParkingDataStandardizable {

    private String nom;

    @JsonProperty(value = "nb_place")
    private int nbPlace;

    @Override
    public String getName() {
        return nom;
    }

    @Override
    public int getCapacity() {
        return nbPlace;
    }

    @Override
    public Integer getRemaining() {
        return null;
    }

    @Override
    public Double getLat() {
        return geo_point_2d != null && geo_point_2d.size() == 2 ? geo_point_2d.get(0) : null;
    }

    @Override
    public Double getLng() {
        return geo_point_2d != null && geo_point_2d.size() == 2 ? geo_point_2d.get(1) : null;
    }

    @Override
    public String getDistance() {
        return dist;
    }

}
