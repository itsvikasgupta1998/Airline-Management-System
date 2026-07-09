package com.vikas.airline.service.impl;

import com.vikas.airline.dto.request.FlightSearchRequest;
import com.vikas.airline.dto.response.FlightResponse;
import com.vikas.airline.entity.Flight;
import com.vikas.airline.mapper.FlightMapper;
import com.vikas.airline.repository.FlightRepository;
import com.vikas.airline.service.FlightService;
import com.vikas.airline.specification.FlightSpecification;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class FlightServiceImpl implements FlightService {

    private final FlightRepository flightRepository;
    private final FlightMapper flightMapper;

    @Override
    @Transactional(readOnly = true)
    public Page<FlightResponse> searchFlights(
            FlightSearchRequest request,
            int page,
            int size,
            String sortBy,
            String sortDir
    ) {

        Sort sort = sortDir.equalsIgnoreCase("desc")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();

        Pageable pageable = PageRequest.of(page, size, sort);

        Page<Flight> flights = flightRepository.findAll(
                FlightSpecification.search(request),
                pageable
        );

        return flights.map(flightMapper::toResponse);
    }
}