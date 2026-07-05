package com.vikas.airline.service;

import com.vikas.airline.dto.request.PassengerInfoRequest;
import com.vikas.airline.dto.response.PassengerInfoResponse;

public interface PassengerService {
    PassengerInfoResponse savePassengerInfo(PassengerInfoRequest request);
}
