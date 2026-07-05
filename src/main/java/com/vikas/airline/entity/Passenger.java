package com.vikas.airline.entity;

import com.vikas.airline.enums.Gender;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(
        name = "passengers",
        indexes = {
                @Index(name = "idx_passenger_email", columnList = "email")
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Passenger extends BaseEntity {

    @Column(
            name = "full_name",
            nullable = false,
            length = 100
    )
    private String fullName;

    @Column(
            nullable = false,
            unique = true,
            length = 150
    )
    private String email;

    @Column(
            name = "contact_number",
            nullable = false,
            length = 15
    )
    private String contactNumber;

    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 10)
    private Gender gender;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "seat_id",
            nullable = false,
            unique = true
    )
    private Seat seat;

    @OneToOne(
            mappedBy = "passenger",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private Baggage baggage;

}