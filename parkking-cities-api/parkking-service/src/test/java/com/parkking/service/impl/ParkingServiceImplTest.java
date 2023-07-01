package com.parkking.service.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.mockito.stubbing.OngoingStubbing;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.web.client.RestClientException;

import com.parkking.connector.cities.data.ParkingDataStandardizable;
import com.parkking.connector.cities.service.CityParkingConnector;
import com.parkking.dto.City;
import com.parkking.dto.Parking;
import com.parkking.exception.ParkkingApiException;
import com.parkking.service.CityService;
import com.parkking.service.ParkingService;

import lombok.AllArgsConstructor;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class ParkingServiceImplTest {

	@AllArgsConstructor
	enum GetParkingsParams {

		UNEXISTING_CITY_CODE("unexistingCode", Optional.empty(), Optional.empty(), Optional.empty(), null, false, null,
				new ParkkingApiException(400), null),

		UNAVAILABLE_CONNECTOR("existingCode", Optional.of(46.58027000081975), Optional.of(0.3491732290149798),
				Optional.of(500), City.builder().build(), false, null, new ParkkingApiException(500), null),

		INVALID_CONNECTOR("existingCode", Optional.of(Double.MAX_VALUE), Optional.of(Double.MIN_VALUE),
				Optional.of(100), City.builder().connector("invalid").build(), false, null,
				new ParkkingApiException(500), null),

		GET_PARKING_EXCEPTION("existingCode", Optional.of(Double.MAX_VALUE), Optional.of(Double.MIN_VALUE),
				Optional.of(100), City.builder().connector("connector").build(), true,
				new RestClientException("Exception from the stubbed connector"), new ParkkingApiException(500),
				List.of()),

		GET_PARKING_LIST("existingCode", Optional.of(0d), Optional.of(0d), Optional.of(100),
				City.builder().connector("connector").build(), true, null, null,
				List.of(of("Parking A", 200, null, 1d, 2d, "100"), of("Parking B", 500, 300, 2d, 3d, "80")));

		public String cityCode;

		public Optional<Double> lat;

		public Optional<Double> lng;

		public Optional<Integer> distance;

		public City stubCity;

		public boolean stubConnectorHasBeenResolved;

		public Throwable stubException;

		public ParkkingApiException expectedException;

		public Collection<ParkingDataStandardizable> parkings;

	}

	@InjectMocks
	private ParkingService service = new ParkingServiceImpl();

	@Mock
	private CityService cityService;

	@Mock
	private BeanFactory beans;

	@ParameterizedTest
	@EnumSource(value = GetParkingsParams.class)
	public void testGetParkings(GetParkingsParams params) throws Exception {
		// Init
		when(cityService.getCityByCode(any())).thenReturn(params.stubCity);

		CityParkingConnector connector = null;
		if (params.stubConnectorHasBeenResolved) {
			connector = mock(CityParkingConnector.class);

			OngoingStubbing<Collection<ParkingDataStandardizable>> stubConnector = when(
					connector.getParkings(any(), any(), any()));
			if (params.stubException != null) {
				stubConnector.thenThrow(params.stubException);
			} else {
				stubConnector.thenReturn(params.parkings);
			}
		}

		when(beans.getBean(anyString(), eq(CityParkingConnector.class))).thenReturn(connector);

		// Invocation
		try {
			List<Parking> actualParkings = service.getParkings(params.cityCode, params.lat, params.lng,
					params.distance);

			// Phase de verification + asserts

			if (params.expectedException != null) {
				fail("L'exception suivante etait attendue", params.expectedException);
			}

			if (params.stubConnectorHasBeenResolved) {
				ArgumentCaptor<String> actualCapturedConnector = ArgumentCaptor.forClass(String.class);
				verify(beans).getBean(actualCapturedConnector.capture(), eq(CityParkingConnector.class));

				assertThat(actualCapturedConnector.getValue()).isEqualTo(params.stubCity.getConnector());
			}

			if (params.parkings != null) {
				List<Parking> expectedParkingList = params.parkings.stream().map(Parking::fromConnectorData)
						.collect(Collectors.toList());
				assertThat(actualParkings).isEqualTo(expectedParkingList);
			}

		} catch (Exception e) {
			// Verification des exceptions attendues
			if (params.expectedException == null) {
				fail("Exception non attendue", e);
			} else {
				assertThat(e).isInstanceOfAny(params.expectedException.getClass());
				assertThat(((ParkkingApiException) e).getHttpCode()).isEqualTo(params.expectedException.getHttpCode());
			}
		}

	}

	private static ParkingDataStandardizable of(String name, int capacity, Integer remaining, Double lat, Double lng,
			String distance) {

		return new ParkingDataStandardizable() {

			@Override
			public Integer getRemaining() {
				return remaining;
			}

			@Override
			public String getName() {
				return name;
			}

			@Override
			public Double getLng() {
				return lng;
			}

			@Override
			public Double getLat() {
				return lat;
			}

			@Override
			public String getDistance() {
				return distance;
			}

			@Override
			public int getCapacity() {
				return capacity;
			}
		};

	}

}
