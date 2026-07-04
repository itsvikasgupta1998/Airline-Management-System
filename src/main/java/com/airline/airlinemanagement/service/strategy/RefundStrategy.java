package com.airline.airlinemanagement.service.strategy;

import com.airline.airlinemanagement.entity.Booking;

public interface RefundStrategy {
    double calculateRefund(Booking booking);
}
