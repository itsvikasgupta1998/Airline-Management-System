package com.vikas.airline.entity;

import com.vikas.airline.enums.BookingStatus;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(
        name = "bookings",
        indexes = {
                @Index(name = "idx_booking_reference", columnList = "booking_reference"),
                @Index(name = "idx_booking_status", columnList = "status")
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Booking extends BaseEntity {

    @Column(
            name = "booking_reference",
            nullable = false,
            unique = true,
            length = 100
    )
    private String bookingReference;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "user_id",
            nullable = false
    )
    private User passenger;

    private LocalDateTime bookingTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "flight_id",
            nullable = false
    )
    private Flight flight;

    @Builder.Default
    @OneToMany(
            mappedBy = "booking",
            cascade = CascadeType.ALL
    )
    private List<Seat> seats = new ArrayList<>();

    @Column(
            name = "total_fare",
            nullable = false,
            precision = 10,
            scale = 2
    )
    private BigDecimal totalFare;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private BookingStatus status;

    @OneToOne(mappedBy = "booking",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private Payment payment;

}