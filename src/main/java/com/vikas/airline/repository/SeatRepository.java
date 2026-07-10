package com.vikas.airline.repository;

import com.vikas.airline.entity.Seat;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface SeatRepository extends JpaRepository<Seat, Long> {

    List<Seat> findByFlightId(Long flightId);
    List<Seat> findByFlightIdAndBookedFalse(Long flightId);
    List<Seat> findByFlightIdAndBookedTrue(Long flightId);
    Optional<Seat> findByFlightIdAndSeatNumber(Long flightId, String seatNumber);
    boolean existsByFlightIdAndSeatNumber(Long flightId, String seatNumber);
    long countByFlightIdAndBookedTrue(Long flightId);
    long countByFlightIdAndBookedFalse(Long flightId);


}