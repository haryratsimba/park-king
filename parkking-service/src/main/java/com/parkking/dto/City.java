package com.parkking.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class City {

	private String code;

	private String label;

	public static City fromEntity(com.parkking.entity.City entity) {
		return City.builder().code(entity.getCode()).label(entity.getLabel()).build();
	}

}
