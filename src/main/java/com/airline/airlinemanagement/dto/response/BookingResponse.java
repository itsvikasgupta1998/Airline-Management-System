package com.airline.airlinemanagement.dto.response;

import com.airline.airlinemanagement.entity.BookingStatus;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
public class BookingResponse {
    private String bookingReference;
    private String flightNumber;
    private List<String> seatsBooked;
    private double totalFare;
    private LocalDateTime bookingTime;
    private BookingStatus status;
}
