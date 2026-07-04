package com.airline.airlinemanagement.service;

import com.airline.airlinemanagement.dto.response.BookingResponse;

import java.nio.file.AccessDeniedException;

public interface BookingService {
    BookingResponse cancelBooking(Long bookingId, String userEmail) throws AccessDeniedException;
}
