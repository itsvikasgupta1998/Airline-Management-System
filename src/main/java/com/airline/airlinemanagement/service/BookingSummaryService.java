package com.airline.airlinemanagement.service;

import com.airline.airlinemanagement.dto.response.BookingSummaryResponse;

import java.util.List;

public interface BookingSummaryService {
    List<BookingSummaryResponse> getUserBookings(String userEmail);

}
