package com.airline.airlinemanagement.repository;

import com.airline.airlinemanagement.entity.Baggage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BaggageRepository extends JpaRepository<Baggage, Long> {}