package com.parkking.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.parkking.dto.Parking;
import com.parkking.service.ParkingService;

@Service
public class ParkingServiceImpl implements ParkingService {

	@Override
	public List<Parking> getParkings(String cityCode, Optional<Double> lat, Optional<Double> lng,
			Optional<Integer> distance) {
		// TODO Auto-generated method stub
		return null;
	}

}
