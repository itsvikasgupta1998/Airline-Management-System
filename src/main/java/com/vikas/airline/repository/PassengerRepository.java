package com.vikas.airline.repository;

import com.vikas.airline.entity.Passenger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface PassengerRepository extends JpaRepository<Passenger, Long>,
        JpaSpecificationExecutor<Passenger> {

    boolean existsByPassportNumberIgnoreCaseAndActiveTrue(String passportNumber);

    boolean existsByEmailIgnoreCaseAndActiveTrue(String email);

    Optional<Passenger> findByIdAndActiveTrue(Long id);

    Page<Passenger> findAllByActiveTrue(Pageable pageable);



}