package com.vikas.airline.service;

import com.vikas.airline.dto.request.BookingSearchRequest;
import com.vikas.airline.dto.request.CancelBookingRequest;
import com.vikas.airline.dto.request.CreateBookingRequest;
import com.vikas.airline.dto.request.UpdateBookingRequest;
import com.vikas.airline.dto.response.BookingResponse;
import org.springframework.data.domain.Page;

public interface BookingService {

    BookingResponse createBooking(CreateBookingRequest request);

    BookingResponse updateBooking(Long id, UpdateBookingRequest request);

    BookingResponse getBookingById(Long id);

    Page<BookingResponse> getAllBookings(
            int page,
            int size,
            String sortBy,
            String sortDir);

    void deleteBooking(Long id);

    void restoreBooking(Long id);

    Page<BookingResponse> searchBookings(
            BookingSearchRequest request,
            int page,
            int size,
            String sortBy,
            String sortDir
    );

    BookingResponse cancelBooking(
            Long id,
            CancelBookingRequest request);

}


