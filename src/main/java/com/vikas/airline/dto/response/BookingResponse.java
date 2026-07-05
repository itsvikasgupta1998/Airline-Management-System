package com.vikas.airline.dto.response;

import com.vikas.airline.enums.BookingStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
public class BookingResponse {
    private String bookingReference;
    private String flightNumber;
    private List<String> seatsBooked;
    private BigDecimal totalFare;
    private LocalDateTime bookingTime;
    private BookingStatus status;
}
