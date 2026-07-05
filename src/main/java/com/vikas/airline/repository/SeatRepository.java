package com.vikas.airline.repository;

import com.vikas.airline.entity.Seat;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SeatRepository extends JpaRepository<Seat, Long> {
    List<Seat> findByFlightIdAndSeatNumberIn(Long flightId, List<String> seatNumbers);
}