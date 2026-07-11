package com.vikas.airline.repository;

import com.vikas.airline.entity.Flight;
import org.jspecify.annotations.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface FlightRepository extends
        JpaRepository<Flight, Long>,
        JpaSpecificationExecutor<Flight> {


    boolean existsByFlightNumberAndDepartureDateAndActiveTrue(
            String flightNumber, LocalDate departureDate);

    Optional<Flight> findByIdAndActiveTrue(Long id);



}