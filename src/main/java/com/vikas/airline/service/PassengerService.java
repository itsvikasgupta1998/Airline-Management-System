package com.vikas.airline.service;

import com.vikas.airline.dto.request.PassengerInfoRequest;
import com.vikas.airline.dto.response.PassengerInfoResponse;
import jakarta.transaction.Transactional;

public interface PassengerService {

    @Transactional
    PassengerInfoResponse savePassengerInfo(PassengerInfoRequest req);

    PassengerInfoResponse createPassenger(
            PassengerInfoRequest request
    );

    PassengerInfoResponse getPassenger(
            Long passengerId
    );

    PassengerInfoResponse updatePassenger(
            Long passengerId,
            PassengerInfoRequest request
    );

}