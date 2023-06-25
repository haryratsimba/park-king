
package com.parkking.connector.cities.opendata.data;

import com.parkking.connector.cities.data.ParkingDataStandardizable;

import lombok.Data;

@Data
public class Record<T extends OpenDataField & ParkingDataStandardizable> {

    private String datasetid;

    private String recordid;

    private T fields;

}
