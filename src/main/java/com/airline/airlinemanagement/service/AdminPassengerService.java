package com.airline.airlinemanagement.service;

import com.airline.airlinemanagement.dto.response.PassengerSummaryResponse;

import java.util.List;

public interface AdminPassengerService {

    List<PassengerSummaryResponse> getPassengersByFlight(Long flightId);

}
