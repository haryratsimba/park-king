
package com.parkking.connector.cities.opendata.data;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.parkking.connector.cities.data.ParkingDataStandardizable;

import lombok.Data;

@Data
public class OpenDataRecord<T extends ParkingDataStandardizable> {

    private List<Record<T>> records = new ArrayList<>();

    public List<T> getStandardizedParkingsList() {
        if (records == null) {
            return new ArrayList<>();
        }

        return records.stream().map(Record::getFields).collect(Collectors.toList());
    }

}
