package com.airline.airlinemanagement.repository;

import com.airline.airlinemanagement.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingRepository extends JpaRepository<Booking, Long> {}

