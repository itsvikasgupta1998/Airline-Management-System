package com.vikas.airline.repository;

import com.vikas.airline.entity.Ticket;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface TicketRepository extends
        JpaRepository<Ticket, Long>,
        JpaSpecificationExecutor<Ticket> {

    Optional<Ticket> findByIdAndActiveTrue(Long id);

    Page<Ticket> findAllByActiveTrue(Pageable pageable);

    Optional<Ticket> findByTicketNumberAndActiveTrue(String ticketNumber);

    Optional<Ticket> findByBookingIdAndActiveTrue(Long bookingId);

    boolean existsByTicketNumber(String ticketNumber);

    boolean existsByBookingIdAndActiveTrue(Long bookingId);


}