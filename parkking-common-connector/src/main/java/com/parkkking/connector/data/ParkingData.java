package com.parkkking.connector.data;

import lombok.Data;

@Data
public class ParkingData {

	private String name;

	private int capacity;

	private Integer remaining;

	private double lat;

	private double lng;

}
