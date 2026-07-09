package com.vikas.airline.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(
        name = "airports",
        indexes = {
                @Index(
                        name = "idx_airport_iata",
                        columnList = "iata_code"
                ),
                @Index(
                        name = "idx_airport_city",
                        columnList = "city"
                ),
                @Index(
                        name = "idx_airport_country",
                        columnList = "country"
                )
        },
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_airport_iata",
                        columnNames = "iata_code"
                ),
                @UniqueConstraint(
                        name = "uk_airport_icao",
                        columnNames = "icao_code"
                )
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Airport extends BaseEntity {

    @Column(nullable = false, length = 100)
    private String airportName;

    @Column(nullable = false, length = 50)
    private String city;

    @Column(nullable = false, length = 50)
    private String state;

    @Column(nullable = false, length = 50)
    private String country;

    @Column(
            name = "iata_code",
            nullable = false,
            length = 3
    )
    private String iataCode;

    @Column(
            name = "icao_code",
            nullable = false,
            length = 4
    )
    private String icaoCode;

    @Column(nullable = false, length = 50)
    private String timezone;

    @Column(nullable = false)
    @Builder.Default
    private Boolean active = true;
}