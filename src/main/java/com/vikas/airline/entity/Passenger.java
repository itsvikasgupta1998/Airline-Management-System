package com.vikas.airline.entity;

import com.vikas.airline.enums.Gender;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@Table(
        name = "passengers",
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_passenger_passport", columnNames = "passport_number"),
                @UniqueConstraint(name = "uk_passenger_email", columnNames = "email")
        },
        indexes = {
                @Index(name = "idx_passenger_last_name", columnList = "last_name"),
                @Index(name = "idx_passenger_nationality", columnList = "nationality"),
                @Index(name = "idx_passenger_phone", columnList = "contact_number")
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Passenger extends BaseEntity {

    @Column(name = "first_name", nullable = false, length = 50)
    private String firstName;

    @Column(name = "last_name", nullable = false, length = 50)
    private String lastName;

    @Column(name = "date_of_birth", nullable = false)
    private LocalDate dateOfBirth;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 10)
    private Gender gender;

    @Column(nullable = false, length = 50)
    private String nationality;

    @Column(name = "passport_number", nullable = false, length = 20)
    private String passportNumber;

    @Column(name = "passport_expiry", nullable = false)
    private LocalDate passportExpiry;

    @Column(unique = true, length = 100)
    private String email;

    @Column(name = "contact_number",nullable = false, length = 15)
    private String phone;

    @Column(name = "guardian_name", length = 100)
    private String guardianName;

    @Column(nullable = false)
    @Builder.Default
    private Boolean active = true;

}