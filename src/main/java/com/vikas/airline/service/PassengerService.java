package com.vikas.airline.service;

import com.vikas.airline.dto.request.CreatePassengerRequest;
import com.vikas.airline.dto.request.PassengerSearchRequest;
import com.vikas.airline.dto.request.UpdatePassengerRequest;
import com.vikas.airline.dto.response.PassengerResponse;
import org.springframework.data.domain.Page;

public interface PassengerService {

    PassengerResponse createPassenger(CreatePassengerRequest request);

    PassengerResponse getPassengerById(Long passengerId);

    Page<PassengerResponse> getAllPassengers(
            int page,
            int size,
            String sortBy,
            String sortDir
    );

    PassengerResponse updatePassenger(
            Long passengerId,
            UpdatePassengerRequest request
    );

    void deletePassenger(Long passengerId);

    void restorePassenger(Long passengerId);

    Page<PassengerResponse> searchPassengers(
            PassengerSearchRequest request,
            int page,
            int size,
            String sortBy,
            String sortDir
    );

}