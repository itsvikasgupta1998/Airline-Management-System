package com.vikas.airline.repository;

import com.vikas.airline.entity.Payment;
import com.vikas.airline.enums.PaymentStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import java.util.Optional;

public interface PaymentRepository extends
        JpaRepository<Payment, Long>,
        JpaSpecificationExecutor<Payment> {

    boolean existsByTransactionId(String transactionId);

    Optional<Payment> findByBookingIdAndActiveTrue(Long bookingId);

    Optional<Payment> findByIdAndActiveTrue(Long id);

    Page<Payment> findAllByActiveTrue(Pageable pageable);

    boolean existsByBookingIdAndPaymentStatus(Long bookingId, PaymentStatus paymentStatus);
}