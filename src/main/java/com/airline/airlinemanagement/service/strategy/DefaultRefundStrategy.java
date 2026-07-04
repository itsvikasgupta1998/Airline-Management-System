package com.airline.airlinemanagement.service.strategy;

import com.airline.airlinemanagement.entity.Booking;
import org.springframework.stereotype.Component;


import java.time.Duration;
import java.time.LocalDateTime;

@Component
public class DefaultRefundStrategy implements RefundStrategy {

    @Override
    public double calculateRefund(Booking booking) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime departure = LocalDateTime.of(
                booking.getFlight().getDepartureDate(),
                booking.getFlight().getDepartureTime()
        );
        Duration duration = Duration.between(LocalDateTime.now(), departure);
        long hoursBeforeFlight = duration.toHours();
        double total = booking.getTotalFare();

        if (hoursBeforeFlight >= 24) return total;
        else if (hoursBeforeFlight >= 3) return total * 0.5;
        else return 0.0;
    }
}
