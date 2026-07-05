package com.vikas.airline.repository;

import com.vikas.airline.entity.Baggage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BaggageRepository extends JpaRepository<Baggage, Long> {}