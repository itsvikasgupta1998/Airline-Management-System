package com.airline.airlinemanagement.service;

import com.airline.airlinemanagement.dto.request.PassengerInfoRequest;
import com.airline.airlinemanagement.dto.response.PassengerInfoResponse;

public interface PassengerService {
    PassengerInfoResponse savePassengerInfo(PassengerInfoRequest request);
}
