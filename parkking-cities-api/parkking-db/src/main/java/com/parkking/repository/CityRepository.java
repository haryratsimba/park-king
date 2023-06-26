package com.parkking.repository;

import java.util.List;

import org.locationtech.jts.geom.Geometry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.parkking.entity.City;

@Repository
public interface CityRepository extends JpaRepository<City, String> {

	@Query(value = "SELECT c FROM City c WHERE within(:point, c.polygonArea)")
	public List<City> findCoveringCity(Geometry point);

}
