package com.vikas.airline.service.impl;

import com.vikas.airline.dto.request.AircraftCreateRequest;
import com.vikas.airline.dto.request.AircraftUpdateRequest;
import com.vikas.airline.dto.response.AircraftResponse;
import com.vikas.airline.entity.Aircraft;
import com.vikas.airline.exception.BadRequestException;
import com.vikas.airline.exception.ResourceNotFoundException;
import com.vikas.airline.mapper.AircraftMapper;
import com.vikas.airline.repository.AircraftRepository;
import com.vikas.airline.service.AdminAircraftService;
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
public class AdminAircraftServiceImpl implements AdminAircraftService {

    private final AircraftRepository aircraftRepository;
    private final AircraftMapper aircraftMapper;

    @Override
    @Transactional
    public AircraftResponse createAircraft(AircraftCreateRequest request) {

        log.info("Creating aircraft with registration number : {}",
                request.getRegistrationNumber());

        if (aircraftRepository.existsByRegistrationNumberIgnoreCaseAndActiveTrue(
                request.getRegistrationNumber())) {

            throw new BadRequestException(
                    "Aircraft with registration number already exists."
            );
        }

        Aircraft aircraft = aircraftMapper.toEntity(request);

        Aircraft savedAircraft = aircraftRepository.save(aircraft);

        log.info("Aircraft created successfully with id : {}",
                savedAircraft.getId());

        return aircraftMapper.toResponse(savedAircraft);
    }

    @Override
    @Transactional
    public AircraftResponse updateAircraft(
            Long aircraftId,
            AircraftUpdateRequest request) {

        log.info("Updating aircraft : {}", aircraftId);

        Aircraft aircraft = aircraftRepository.findByIdAndActiveTrue(aircraftId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Aircraft not found with id : " + aircraftId));

        aircraftMapper.updateAircraftFromRequest(request, aircraft);

        Aircraft updatedAircraft = aircraftRepository.save(aircraft);

        log.info("Aircraft updated successfully : {}", aircraftId);

        return aircraftMapper.toResponse(updatedAircraft);
    }

    @Override
    public AircraftResponse getAircraftById(Long aircraftId) {

        Aircraft aircraft = aircraftRepository.findByIdAndActiveTrue(aircraftId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Aircraft not found with id : " + aircraftId));

        return aircraftMapper.toResponse(aircraft);
    }

    @Override
    public Page<AircraftResponse> getAllAircraft(Pageable pageable) {

        return aircraftRepository.findByActiveTrue(pageable)
                .map(aircraftMapper::toResponse);
    }

    @Override
    @Transactional
    public void deleteAircraft(Long aircraftId) {

        log.info("Soft deleting aircraft {}", aircraftId);
        Aircraft aircraft = aircraftRepository.findById(aircraftId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Aircraft not found with id : " + aircraftId));

        if (!Boolean.TRUE.equals(aircraft.getActive())) {
            throw new BadRequestException("Aircraft is already deleted.");
        }

        aircraft.setActive(false);
        aircraftRepository.save(aircraft);
        log.info("Aircraft {} marked as inactive", aircraftId);
    }

    @Transactional
    public void restoreAircraft(Long aircraftId) {

        Aircraft aircraft = aircraftRepository.findById(aircraftId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Aircraft not found"));

        if (Boolean.TRUE.equals(aircraft.getActive())) {
            throw new BadRequestException("Aircraft is already active.");
        }

        aircraft.setActive(true);

        aircraftRepository.save(aircraft);
    }

}