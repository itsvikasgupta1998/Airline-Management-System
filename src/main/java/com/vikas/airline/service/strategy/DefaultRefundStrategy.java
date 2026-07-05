package com.vikas.airline.service.strategy;

import com.vikas.airline.entity.Booking;
import org.springframework.stereotype.Component;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Duration;
import java.time.LocalDateTime;

@Component
public class DefaultRefundStrategy implements RefundStrategy {

    @Override
    public BigDecimal calculateRefund(Booking booking) {

        LocalDateTime departure = LocalDateTime.of(
                booking.getFlight().getDepartureDate(),
                booking.getFlight().getDepartureTime()
        );

        long hoursBeforeFlight = Duration
                .between(LocalDateTime.now(), departure)
                .toHours();

        BigDecimal total = booking.getTotalFare();

        BigDecimal refund;

        if (hoursBeforeFlight >= 24) {
            refund = total;

        } else if (hoursBeforeFlight >= 3) {
            refund = total.multiply(BigDecimal.valueOf(0.5));

        } else {
            refund = BigDecimal.ZERO;
        }

        return refund.setScale(2, RoundingMode.HALF_UP);
    }
}