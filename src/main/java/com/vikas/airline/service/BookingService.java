package com.vikas.airline.service;

import com.vikas.airline.dto.response.BookingResponse;

import java.nio.file.AccessDeniedException;

public interface BookingService {
    BookingResponse cancelBooking(Long bookingId, String userEmail) throws AccessDeniedException;
}
