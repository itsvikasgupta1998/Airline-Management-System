package com.airline.airlinemanagement.controller;


import com.airline.airlinemanagement.dto.response.BookingSummaryResponse;
import com.airline.airlinemanagement.service.BookingSummaryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/bookings")
@RequiredArgsConstructor
public class BookingSummaryController {

    private BookingSummaryService summaryService;
    @GetMapping("/my-bookings")
    public ResponseEntity<List<BookingSummaryResponse>> getMyBookings(Authentication auth) {
        return ResponseEntity.ok(summaryService.getUserBookings(auth.getName()));
    }

}
