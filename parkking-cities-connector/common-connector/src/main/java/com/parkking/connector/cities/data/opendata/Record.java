
package com.parkking.connector.cities.data.opendata;

import lombok.Data;

@Data
public class Record<T> {

    private String datasetid;

    private String recordid;

    private T fields;

}
