package com.vikas.airline.controller;

import com.vikas.airline.dto.request.BookingRequest;
import com.vikas.airline.dto.response.BookingResponse;
import com.vikas.airline.service.impl.BookingServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.nio.file.AccessDeniedException;

@RestController
@RequestMapping("/bookings")
@RequiredArgsConstructor
public class BookingController {

    private final BookingServiceImpl bookingServiceImpl;

    @PreAuthorize("hasRole('PASSENGER')")
    @PostMapping
    public ResponseEntity<BookingResponse> bookFlight(@RequestBody BookingRequest request,
                                                      Authentication authentication) {
        String userEmail = authentication.getName();
        return ResponseEntity.ok(bookingServiceImpl.bookFlight(request, userEmail));
    }

    @DeleteMapping("/{bookingId}")
    public ResponseEntity<BookingResponse> cancelBooking(@PathVariable Long bookingId, Authentication auth) throws AccessDeniedException {
        String userEmail = auth.getName();
        return ResponseEntity.ok(bookingServiceImpl.cancelBooking(bookingId, userEmail));
    }

}
