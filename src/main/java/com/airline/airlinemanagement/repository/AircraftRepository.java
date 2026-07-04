package com.airline.airlinemanagement.repository;

import com.airline.airlinemanagement.entity.Aircraft;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AircraftRepository extends JpaRepository<Aircraft, Long> {}
