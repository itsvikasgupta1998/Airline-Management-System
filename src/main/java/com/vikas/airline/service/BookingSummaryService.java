package com.vikas.airline.service;

import com.vikas.airline.dto.response.BookingSummaryResponse;
import org.springframework.data.domain.Page;

import java.util.List;

public interface BookingSummaryService {

    List<BookingSummaryResponse> getUserBookings(String userEmail);

    Page<BookingSummaryResponse> getMyBookings(
            int page,
            int size,
            String sortBy,
            String sortDir
    );

}