package com.parkking.service.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.GeometryFactory;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.parkking.dto.City;
import com.parkking.repository.CityRepository;
import com.parkking.service.CityService;

import lombok.AllArgsConstructor;

@ExtendWith(MockitoExtension.class)
public class CityServiceImplTest {

	@AllArgsConstructor
	enum GetAllCitiesParams {

		EMPTY_CITIES(List.of()),

		CITY_B(List.of(com.parkking.entity.City.builder().code("cityB").label("City B").build())),

		TWO_CITIES(List.of(com.parkking.entity.City.builder().code("cityC").label("City C").build(),
				com.parkking.entity.City.builder().code("cityD").label("City D").build()));

		public List<com.parkking.entity.City> cities;

	}

	@AllArgsConstructor
	enum GetCoordsParams {

		COORDS_MIN_WITH_CITY_A(Double.MIN_VALUE, Double.MIN_VALUE,
				List.of(com.parkking.entity.City.builder().code("cityA").label("City A").build())),

		COORDS_MAX_WITH_CITY_B(Double.MAX_VALUE, Double.MAX_VALUE,
				List.of(com.parkking.entity.City.builder().code("cityB").label("City B").build())),

		COORDS_NEGATIVE_WITH_TWO_CITIES(-1d, -45.89909,
				List.of(com.parkking.entity.City.builder().code("cityC").label("City C").build(),
						com.parkking.entity.City.builder().code("cityD").label("City D").build())),

		COORDS_ZERO_WITH_NO_CITY(0d, 0d, List.of());

		public double lat;

		public double lng;

		public List<com.parkking.entity.City> cities;

	}

	@InjectMocks
	private CityService service = new CityServiceImpl();

	@Mock
	private CityRepository repository;

	@ParameterizedTest
	@EnumSource(value = GetAllCitiesParams.class)
	public void testGetAllCities(GetAllCitiesParams params) {
		// Init
		when(repository.findAll()).thenReturn(params.cities);

		// Invocation
		List<City> actualCities = service.getAllCities();
		
		// Phase de verification + asserts
		List<City> expectedCityList = params.cities.stream().map(City::fromEntity).collect(Collectors.toList());
		assertThat(actualCities).isEqualTo(expectedCityList);
	}

	@SuppressWarnings("unchecked")
	@ParameterizedTest
	@EnumSource(value = GetCoordsParams.class)
	public void testGetCoordsBelongingCities(GetCoordsParams params) {

		// Init
		when(repository.findCoveringCity(any())).thenReturn(params.cities);
		ArgumentCaptor<Geometry> actualCapturedGeometry = ArgumentCaptor.forClass(Geometry.class);

		// Invocation
		List<City> actualCities = service.getCoordsBelongingCities(params.lat, params.lng);

		// Phase de verification + asserts
		verify(repository).findCoveringCity(actualCapturedGeometry.capture());

		GeometryFactory geometryFactory = new GeometryFactory();
		Geometry expectedPoint = geometryFactory.createPoint(new Coordinate(params.lng, params.lat));
		
		assertThat(actualCapturedGeometry.getValue()).isEqualTo(expectedPoint);
		
		List<City> expectedCityList = params.cities.stream().map(City::fromEntity).collect(Collectors.toList());
		assertThat(actualCities).isEqualTo(expectedCityList);
	}

}
