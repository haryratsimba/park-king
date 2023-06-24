package com.parkking.dto;

import lombok.Data;

@Data
public class Parking {

	private String name;

	private int capacity;

	private Integer remaining;

	private double lat;

	private double lng;

}
