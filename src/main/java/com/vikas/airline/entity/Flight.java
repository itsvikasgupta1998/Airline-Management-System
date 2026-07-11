package com.vikas.airline.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(
        name = "flights",
        indexes = {
                @Index(name = "idx_flight_number", columnList = "flight_number"),
                @Index(name = "idx_route", columnList = "source_airport_id,destination_airport_id"),
                @Index(name = "idx_departure_date", columnList = "departure_date")
        },
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_flight_number_date",
                        columnNames = {
                                "flight_number",
                                "departure_date"
                        }
                )
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Flight extends BaseEntity {

    @Column(name = "flight_number", nullable = false, length = 20)
    private String flightNumber;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "source_airport_id",
            nullable = false
    )
    private Airport sourceAirport;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "destination_airport_id",
            nullable = false
    )
    private Airport destinationAirport;

    @Column(name = "departure_date", nullable = false)
    private LocalDate departureDate;

    @Column(name = "departure_time", nullable = false)
    private LocalTime departureTime;

    @Column(name = "arrival_time", nullable = false)
    private LocalTime arrivalTime;

    @Column(nullable = false, length = 100)
    private String airline;

    @Column(name = "total_seats", nullable = false)
    private Integer totalSeats;

    @Column(name = "available_seats", nullable = false)
    private Integer availableSeats;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal fare;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "aircraft_id",
            nullable = false,
            unique = true
    )
    private Aircraft aircraft;


    @Builder.Default
    @OneToMany(
            mappedBy = "flight",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<Seat> seats = new ArrayList<>();

    @Column(nullable = false)
    @Builder.Default
    private Boolean active = true;

}