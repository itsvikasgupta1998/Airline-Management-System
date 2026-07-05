package com.vikas.airline.service;

import com.vikas.airline.dto.response.BookingSummaryResponse;

import java.util.List;

public interface BookingSummaryService {
    List<BookingSummaryResponse> getUserBookings(String userEmail);

}
