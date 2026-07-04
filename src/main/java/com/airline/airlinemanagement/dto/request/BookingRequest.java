package com.airline.airlinemanagement.dto.request;

import lombok.Data;

import java.util.List;

@Data
public class BookingRequest {
    private Long flightId;
    private List<String> seatNumbers;
}
