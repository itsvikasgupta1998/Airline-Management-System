package com.vikas.airline.entity;

import com.vikas.airline.enums.SeatClass;
import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;

@Entity
@Table(
        name = "seats",
        indexes = {
                @Index(
                        name = "idx_seat_flight_number",
                        columnList = "flight_id,seat_number"
                )
        },
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_flight_seat",
                        columnNames = {
                                "flight_id",
                                "seat_number"
                        }
                )
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Seat extends BaseEntity {

    @Column(
            name = "seat_number",
            nullable = false,
            length = 10
    )
    private String seatNumber;

    @Enumerated(EnumType.STRING)
    @Column(
            name = "seat_class",
            nullable = false,
            length = 20
    )
    private SeatClass seatClass;

    @Builder.Default
    @Column(nullable = false)
    private boolean booked = false;

    @Column(
            nullable = false,
            precision = 10,
            scale = 2
    )
    private BigDecimal price;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "flight_id",
            nullable = false
    )
    private Flight flight;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "booking_id")
    private Booking booking;

    @OneToOne(mappedBy = "seat")
    private Passenger passenger;

}