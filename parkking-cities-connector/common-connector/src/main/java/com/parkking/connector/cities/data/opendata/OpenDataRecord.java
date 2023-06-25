package com.parkking.connector.cities.data.opendata;

import java.util.List;

import lombok.Data;

@Data
public class OpenDataRecord<T> {

    private List<Record<T>> records;

}
