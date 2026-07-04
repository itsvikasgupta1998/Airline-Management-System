package com.airline.airlinemanagement.repository;

import com.airline.airlinemanagement.entity.Passenger;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PassengerRepository extends JpaRepository<Passenger, Long> {}