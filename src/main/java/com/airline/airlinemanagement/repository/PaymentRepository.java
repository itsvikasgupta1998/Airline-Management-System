package com.airline.airlinemanagement.repository;

import com.airline.airlinemanagement.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment, Long> {}
