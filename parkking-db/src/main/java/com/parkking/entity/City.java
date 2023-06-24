package com.parkking.entity;

import org.locationtech.jts.geom.Polygon;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class City {

	@Id
	@Column(length = 50)
	private String code;

	private String label;

	private Polygon polygonArea;

	@Column(columnDefinition = "boolean default false")
	private boolean isDisabled;

	private String module;

}