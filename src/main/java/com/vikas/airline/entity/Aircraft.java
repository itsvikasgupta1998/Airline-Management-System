package com.vikas.airline.entity;

import com.vikas.airline.enums.AircraftStatus;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(
        name = "aircraft",
        indexes = {
                @Index(
                        name = "idx_registration_number",
                        columnList = "registration_number"
                )
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Aircraft extends BaseEntity {

    @Column(nullable = false, length = 100)
    private String model;

    @Column(nullable = false, length = 100)
    private String manufacturer;

    @Column(nullable = false)
    private Integer capacity;

    @Column(
            name = "registration_number",
            nullable = false,
            unique = true,
            length = 30
    )
    private String registrationNumber;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    @Builder.Default
    private AircraftStatus status = AircraftStatus.ACTIVE;

    @OneToOne(mappedBy = "aircraft")
    private Flight flight;

}