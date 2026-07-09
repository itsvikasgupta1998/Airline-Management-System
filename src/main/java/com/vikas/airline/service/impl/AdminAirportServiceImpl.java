package com.vikas.airline.service.impl;

import com.vikas.airline.dto.request.AirportCreateRequest;
import com.vikas.airline.dto.request.AirportUpdateRequest;
import com.vikas.airline.dto.response.AirportResponse;
import com.vikas.airline.entity.Airport;
import com.vikas.airline.exception.BadRequestException;
import com.vikas.airline.exception.ResourceNotFoundException;
import com.vikas.airline.mapper.AirportMapper;
import com.vikas.airline.repository.AirportRepository;
import com.vikas.airline.service.AdminAirportService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AdminAirportServiceImpl implements AdminAirportService {

    private final AirportRepository airportRepository;
    private final AirportMapper airportMapper;

    @Override
    @Transactional
    public AirportResponse createAirport(AirportCreateRequest request) {

        log.info("Creating airport with IATA code : {}",
                request.getIataCode());

        if (airportRepository.existsByIataCodeIgnoreCaseAndActiveTrue(
                request.getIataCode())) {

            throw new BadRequestException(
                    "Airport with IATA code already exists."
            );
        }

        if (airportRepository.existsByIcaoCodeIgnoreCaseAndActiveTrue(
                request.getIcaoCode())) {

            throw new BadRequestException(
                    "Airport with ICAO code already exists."
            );
        }

        Airport airport = airportMapper.toEntity(request);

        Airport savedAirport = airportRepository.save(airport);

        log.info("Airport created successfully with id : {}",
                savedAirport.getId());

        return airportMapper.toResponse(savedAirport);
    }

    @Override
    @Transactional
    public AirportResponse updateAirport(
            Long airportId,
            AirportUpdateRequest request) {

        log.info("Updating airport : {}", airportId);

        Airport airport = airportRepository
                .findByIdAndActiveTrue(airportId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Airport not found with id : " + airportId));

        if (request.getIataCode() != null &&
                airportRepository.existsByIataCodeIgnoreCaseAndActiveTrueAndIdNot(
                        request.getIataCode(),
                        airportId)) {

            throw new BadRequestException(
                    "Airport with IATA code already exists."
            );
        }

        if (request.getIcaoCode() != null &&
                airportRepository.existsByIcaoCodeIgnoreCaseAndActiveTrueAndIdNot(
                        request.getIcaoCode(),
                        airportId)) {

            throw new BadRequestException(
                    "Airport with ICAO code already exists."
            );
        }

        airportMapper.updateAirportFromRequest(request, airport);

        Airport updatedAirport = airportRepository.save(airport);

        log.info("Airport updated successfully : {}",
                airportId);

        return airportMapper.toResponse(updatedAirport);
    }

    @Override
    public AirportResponse getAirportById(Long airportId) {

        Airport airport = airportRepository
                .findByIdAndActiveTrue(airportId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Airport not found with id : " + airportId));

        return airportMapper.toResponse(airport);
    }

    @Override
    public Page<AirportResponse> getAllAirports(Pageable pageable) {

        return airportRepository
                .findByActiveTrue(pageable)
                .map(airportMapper::toResponse);
    }

    @Override
    @Transactional
    public void deleteAirport(Long airportId) {

        Airport airport = airportRepository
                .findById(airportId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Airport not found with id : " + airportId));

        if (!Boolean.TRUE.equals(airport.getActive())) {
            throw new BadRequestException("Airport is already deleted.");
        }

        airport.setActive(false);

        airportRepository.save(airport);

        log.info("Airport deleted successfully : {}",
                airportId);
    }

    @Override
    @Transactional
    public void restoreAirport(Long airportId) {

        Airport airport = airportRepository
                .findById(airportId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Airport not found with id : " + airportId));

        if (Boolean.TRUE.equals(airport.getActive())) {
            throw new BadRequestException("Airport is already active.");
        }

        airport.setActive(true);

        airportRepository.save(airport);

        log.info("Airport restored successfully : {}",
                airportId);
    }
}