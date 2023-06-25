package com.parkking.connector.cities.opendata.data;

import java.util.List;

import lombok.Data;

@Data
public class OpenDataField {
    
    protected List<Double> geo_point_2d;
    
    protected String dist;

}
