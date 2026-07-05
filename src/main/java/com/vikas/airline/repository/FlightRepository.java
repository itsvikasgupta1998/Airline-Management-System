package com.vikas.airline.repository;

import com.vikas.airline.entity.Flight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface FlightRepository extends JpaRepository<Flight, Long> {

    List<Flight> findBySourceAndDestinationAndDepartureDate(String source, String destination, LocalDate departureDate);
}
