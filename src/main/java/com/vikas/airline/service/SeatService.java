package com.vikas.airline.service;

import com.vikas.airline.dto.response.SeatResponse;
import com.vikas.airline.dto.response.SeatSummaryResponse;

import java.util.List;

public interface SeatService {

    List<SeatResponse> getAllSeats(Long flightId);

    List<SeatResponse> getAvailableSeats(Long flightId);

    List<SeatResponse> getBookedSeats(Long flightId);

    SeatResponse getSeatBySeatNumber(Long flightId, String seatNumber);

    SeatSummaryResponse getSeatSummary(Long flightId);

}