package com.vikas.airline.repository;

import com.vikas.airline.entity.Booking;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface BookingRepository extends JpaRepository<Booking, Long>,
        JpaSpecificationExecutor<Booking> {

    Optional<Booking> findByIdAndActiveTrue(Long id);
    Page<Booking> findAllByActiveTrue(Pageable pageable);

    boolean existsByPassengerIdAndFlightIdAndActiveTrue(
            Long passengerId,
            Long flightId);

}