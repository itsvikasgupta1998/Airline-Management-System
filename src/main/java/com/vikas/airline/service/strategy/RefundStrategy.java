package com.vikas.airline.service.strategy;

import com.vikas.airline.entity.Booking;
import java.math.BigDecimal;

public interface RefundStrategy {
    BigDecimal calculateRefund(Booking booking);
}
