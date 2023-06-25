
package com.parkking.dto;

import com.parkking.connector.cities.data.ParkingDataStandardizable;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Parking {

    private String name;

    private int capacity;

    private Integer remaining;

    private double lat;

    private double lng;
    
    private String distance;

    public static Parking fromConnectorData(ParkingDataStandardizable data) {
        // @formatter:off
	    return Parking.builder()
	            .name(data.getName())
	            .capacity(data.getCapacity())
	            .remaining(data.getRemaining())
	            .lat(data.getLat())
	            .lng(data.getLng())
	            .distance(data.getDistance())
	            .build();
        // @formatter:on
    }

}
