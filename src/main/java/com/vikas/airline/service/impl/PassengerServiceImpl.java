package com.vikas.airline.service.impl;

import com.vikas.airline.dto.request.CreatePassengerRequest;
import com.vikas.airline.dto.request.PassengerSearchRequest;
import com.vikas.airline.dto.request.UpdatePassengerRequest;
import com.vikas.airline.dto.response.PassengerResponse;
import com.vikas.airline.entity.Passenger;
import com.vikas.airline.exception.BadRequestException;
import com.vikas.airline.exception.DuplicateResourceException;
import com.vikas.airline.exception.ResourceNotFoundException;
import com.vikas.airline.mapper.PassengerMapper;
import com.vikas.airline.repository.PassengerRepository;
import com.vikas.airline.service.PassengerService;
import com.vikas.airline.specification.PassengerSpecification;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.time.Period;

@Slf4j
@Service
@RequiredArgsConstructor
public class PassengerServiceImpl implements PassengerService {

    private final PassengerRepository passengerRepository;
    private final PassengerMapper passengerMapper;

    @Override
    @Transactional
    public PassengerResponse createPassenger(CreatePassengerRequest request) {

        log.info("Creating passenger with passport number: {}", request.getPassportNumber());

        validateDuplicatePassport(request.getPassportNumber());

        validateDuplicateEmail(request.getEmail());

        validateGuardian(request);

        Passenger passenger = passengerMapper.toEntity(request);

        Passenger savedPassenger = passengerRepository.save(passenger);

        return buildPassengerResponse(savedPassenger);
    }

    @Override
    @Transactional(readOnly = true)
    public PassengerResponse getPassengerById(Long passengerId) {

        log.info("Fetching passenger with ID: {}", passengerId);

        Passenger passenger = getPassengerOrThrow(passengerId);

        return buildPassengerResponse(passenger);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PassengerResponse> getAllPassengers(
            int page,
            int size,
            String sortBy,
            String sortDir) {

        log.info("Fetching passengers. Page: {}, Size: {}, SortBy: {}, SortDir: {}",
                page, size, sortBy, sortDir);

        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name())
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(page, size, sort);

        Page<Passenger> passengers = passengerRepository.findAllByActiveTrue(pageable);

        return passengers.map(this::buildPassengerResponse);
    }

    @Override
    @Transactional
    public PassengerResponse updatePassenger(Long passengerId,
                                             UpdatePassengerRequest request) {

        log.info("Updating passenger with ID: {}", passengerId);

        Passenger passenger = getPassengerOrThrow(passengerId);
        validateDuplicatePassportForUpdate(passenger, request.getPassportNumber());
        validateDuplicateEmailForUpdate(passenger, request.getEmail());
        passengerMapper.updatePassengerFromRequest(request, passenger);
        Passenger updatedPassenger = passengerRepository.save(passenger);
        return buildPassengerResponse(updatedPassenger);
    }

    @Override
    @Transactional
    public void deletePassenger(Long passengerId) {

        log.info("Deleting passenger with ID: {}", passengerId);

        Passenger passenger = getPassenger(passengerId);
        if (!passenger.getActive()) {
            throw new BadRequestException(
                    "Passenger is already deleted.");
        }

        passenger.setActive(false);

        passengerRepository.save(passenger);

        log.info("Passenger deleted successfully. ID: {}", passengerId);
    }

    @Transactional
    @Override
    public void restorePassenger(Long passengerId) {

        Passenger passenger = getPassenger(passengerId);
        if (passenger.getActive()) {
            throw new BadRequestException(
                    "Passenger is already active.");
        }

        passenger.setActive(true);

        passengerRepository.save(passenger);

        log.info("Passenger restored successfully. ID: {}", passengerId);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PassengerResponse> searchPassengers(
            PassengerSearchRequest request,
            int page,
            int size,
            String sortBy,
            String sortDir) {

        Pageable pageable = PageRequest.of(
                page,
                size,
                Sort.by(
                        sortDir.equalsIgnoreCase("asc")
                                ? Sort.Direction.ASC
                                : Sort.Direction.DESC,
                        sortBy));
        return passengerRepository.findAll(PassengerSpecification.search(request),
                        pageable)
                .map(this::buildPassengerResponse);

    }

                              /*HELPER METHODS*/

    private Passenger getPassengerOrThrow(Long passengerId) {
        return passengerRepository.findByIdAndActiveTrue(passengerId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Passenger", passengerId));
    }

    private void validateDuplicatePassport(String passportNumber) {

        if (passengerRepository.existsByPassportNumberIgnoreCaseAndActiveTrue(passportNumber)) {
            throw new DuplicateResourceException(
                    "Passenger already exists with passport number: " + passportNumber);
        }
    }

    private void validateDuplicateEmail(String email) {

        if (email != null
                && !email.isBlank()
                && passengerRepository.existsByEmailIgnoreCaseAndActiveTrue(email)) {

            throw new DuplicateResourceException(
                    "Passenger already exists with email: " + email);
        }
    }

    private void validateGuardian(CreatePassengerRequest request) {

        int age = Period.between(
                request.getDateOfBirth(),
                LocalDate.now()
        ).getYears();

        if (age < 18 && (request.getGuardianName() == null
                        || request.getGuardianName().isBlank())) {

            throw new BadRequestException(
                    "Guardian name is required for minor passengers.");
        }
    }

    private PassengerResponse buildPassengerResponse(Passenger passenger) {

        PassengerResponse response = passengerMapper.toResponse(passenger);

        response.setFullName(
                passenger.getFirstName() + " " + passenger.getLastName()
        );

        response.setAge(
                Period.between(
                        passenger.getDateOfBirth(),
                        LocalDate.now()
                ).getYears()
        );

        return response;
    }

    private void validateDuplicatePassportForUpdate(Passenger passenger,
                                                    String passportNumber) {

        if (!passenger.getPassportNumber().equalsIgnoreCase(passportNumber)
                && passengerRepository.existsByPassportNumberIgnoreCaseAndActiveTrue(passportNumber)) {

            throw new DuplicateResourceException(
                    "Passenger already exists with passport number: " + passportNumber
            );
        }
    }

    private void validateDuplicateEmailForUpdate(Passenger passenger,
                                                 String email) {

        if (email != null
                && !email.isBlank()
                && !email.equalsIgnoreCase(passenger.getEmail())
                && passengerRepository.existsByEmailIgnoreCaseAndActiveTrue(email)) {

            throw new DuplicateResourceException(
                    "Passenger already exists with email: " + email
            );
        }
    }

    private Passenger getPassenger(Long id) {
        return passengerRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Passenger not found with id: " + id));
    }

}