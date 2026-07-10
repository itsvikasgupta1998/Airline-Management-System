package com.vikas.airline.entity;

import com.vikas.airline.enums.SeatClass;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(
        name = "seats",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_flight_seat_number",
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

    @Column(nullable = false, length = 10)
    private String seatNumber;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private SeatClass seatClass;

    @Column(nullable = false)
    private Boolean booked = false;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal price;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "flight_id",
            nullable = false,
            foreignKey = @ForeignKey(name = "fk_seat_flight")
    )
    private Flight flight;
}