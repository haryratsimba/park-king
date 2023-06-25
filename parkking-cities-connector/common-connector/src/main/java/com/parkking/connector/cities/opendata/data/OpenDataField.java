package com.parkking.connector.cities.opendata.data;

import java.util.List;

import lombok.Data;

/**
 * <pre>
 * "records": [
        {
            "datasetid": "mobilite-parkings-grand-poitiers-donnees-metiers",
            "recordid": "e24d0474ce8282db403e8cbf61c94069c1b89460",
            "fields": { <= Ce champ
                ...
                "geo_point_2d": [
                    46.58374770935979,
                    0.3324910697092901
                ],
                dist: "6.153282543619582"
            }
 * </pre>
 * 
 * @author hary.ratsimba
 *
 */
@Data
public class OpenDataField {
    
    protected List<Double> geo_point_2d;
    
    protected String dist;

}
