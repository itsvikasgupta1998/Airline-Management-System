package com.vikas.airline.service;

import com.vikas.airline.dto.response.PassengerSummaryResponse;

import java.util.List;

public interface AdminPassengerService {

    List<PassengerSummaryResponse> getPassengersByFlight(Long flightId);

}
