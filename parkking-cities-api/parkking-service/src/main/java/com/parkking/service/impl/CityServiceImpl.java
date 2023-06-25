package com.parkking.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.GeometryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.parkking.dto.City;
import com.parkking.repository.CityRepository;
import com.parkking.service.CityService;

@Service
public class CityServiceImpl implements CityService {

	@Autowired
	private CityRepository repository;

	@Override
	public List<City> getAllCities() {
		return repository.findAll().stream().map(City::fromEntity).collect(Collectors.toList());
	}

	@Override
	public List<City> getCoordsBelongingCities(double lat, double lng) {
		GeometryFactory geometryFactory = new GeometryFactory();
		Geometry point = geometryFactory.createPoint(new Coordinate(lng, lat));
		return repository.findCoveringCity(point).stream().map(City::fromEntity).collect(Collectors.toList());
	}

	@Override
	public City getCityByCode(String cityCode) {
		Optional<com.parkking.entity.City> entity = repository.findById(cityCode);
		if (entity.isPresent() && !entity.get().isDisabled()) {
			return City.fromEntity(entity.get());
		} else {
			return null;
		}
	}

}
