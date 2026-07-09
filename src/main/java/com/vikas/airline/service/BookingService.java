package com.vikas.airline.service;

import com.vikas.airline.dto.request.BookingRequest;
import com.vikas.airline.dto.response.BookingResponse;
import jakarta.transaction.Transactional;

import java.nio.file.AccessDeniedException;

public interface BookingService {


    @Transactional
    BookingResponse cancelBooking(Long bookingId, String userEmail) throws AccessDeniedException;

    BookingResponse createBooking(
            BookingRequest request
    );

    BookingResponse getBookingById(
            Long bookingId
    );


    BookingResponse cancelBooking(
            Long bookingId
    );

}