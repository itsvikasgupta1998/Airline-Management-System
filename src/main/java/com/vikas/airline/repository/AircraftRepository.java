package com.vikas.airline.repository;

import com.vikas.airline.entity.Aircraft;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;



public interface AircraftRepository extends JpaRepository<Aircraft, Long> {

    Page<Aircraft> findByActiveTrue(Pageable pageable);

    Optional<Aircraft> findByIdAndActiveTrue(Long id);

    boolean existsByRegistrationNumberIgnoreCaseAndActiveTrue(String registrationNumber);

}