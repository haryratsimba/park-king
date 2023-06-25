
package com.parkking.connector.cities.opendata.data;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import com.parkking.connector.cities.data.ParkingDataStandardizable;

import lombok.Data;

/**
 * <pre>
 * "records": [ <= Ce champ
 *      ...
 *  ]
 * </pre>
 * 
 * @author hary.ratsimba
 *
 */
@Data
public class OpenDataRecord<T extends OpenDataField & ParkingDataStandardizable> {

    private List<Record<T>> records = new ArrayList<>();

    public Collection<ParkingDataStandardizable> getStandardizedParkingsList() {
        if (records == null) {
            return new ArrayList<>();
        }

        return records.stream().map(Record::getFields).collect(Collectors.toList());
    }

}
