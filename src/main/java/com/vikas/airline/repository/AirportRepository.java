package com.vikas.airline.repository;

import com.vikas.airline.entity.Airport;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface AirportRepository extends
        JpaRepository<Airport, Long>,
        JpaSpecificationExecutor<Airport> {

    Page<Airport> findByActiveTrue(Pageable pageable);

    Optional<Airport> findByIdAndActiveTrue(Long id);

    Optional<Airport> findByIataCodeIgnoreCaseAndActiveTrue(String iataCode);

    Optional<Airport> findByIcaoCodeIgnoreCaseAndActiveTrue(String icaoCode);

    boolean existsByIataCodeIgnoreCaseAndActiveTrue(String iataCode);

    boolean existsByIcaoCodeIgnoreCaseAndActiveTrue(String icaoCode);

    boolean existsByIataCodeIgnoreCaseAndActiveTrueAndIdNot(
            String iataCode,
            Long id
    );

    boolean existsByIcaoCodeIgnoreCaseAndActiveTrueAndIdNot(
            String icaoCode,
            Long id
    );


}