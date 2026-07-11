package com.vikas.airline.entity;

import com.vikas.airline.enums.BookingStatus;
import com.vikas.airline.enums.PaymentStatus;
import com.vikas.airline.enums.TravelClass;
import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(
        name = "bookings",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_booking_reference",
                        columnNames = "booking_reference"
                )
        },
        indexes = {

                @Index(
                        name = "idx_booking_reference",
                        columnList = "booking_reference"
                ),

                @Index(
                        name = "idx_booking_status",
                        columnList = "booking_status"
                ),

                @Index(
                        name = "idx_booking_date",
                        columnList = "booking_date"
                ),

                @Index(
                        name = "idx_booking_user",
                        columnList = "user_id"
                ),

                @Index(
                        name = "idx_booking_flight",
                        columnList = "flight_id"
                ),

                @Index(
                        name = "idx_booking_passenger",
                        columnList = "passenger_id"
                )
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
            length = 20
    )
    private String bookingReference;

    @Enumerated(EnumType.STRING)
    @Column(
            name = "booking_status",
            nullable = false,
            length = 20
    )
    private BookingStatus bookingStatus;

    @Enumerated(EnumType.STRING)
    @Column(
            name = "payment_status",
            nullable = false,
            length = 20
    )
    private PaymentStatus paymentStatus;

    @Enumerated(EnumType.STRING)
    @Column(
            name = "travel_class",
            nullable = false,
            length = 30
    )
    private TravelClass travelClass;

    @Column(
            name = "booking_date",
            nullable = false
    )
    private LocalDateTime bookingDate;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal baseFare;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal tax;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal serviceFee;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal discount;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal totalFare;

    @Column(nullable = false, length = 3)
    @Builder.Default
    private String currency = "INR";

    @Column(name = "cancelled_at")
    private LocalDateTime cancelledAt;

    @Column(name = "cancellation_reason")
    private String cancellationReason;

    @Column(length = 500)
    private String remarks;

    @Column(nullable = false)
    @Builder.Default
    private Boolean active = true;

    // ==========================
    // Relationships
    // ==========================

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "user_id",
            nullable = false,
            foreignKey = @ForeignKey(name = "fk_booking_user")
    )
    private User user;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "flight_id",
            nullable = false,
            foreignKey = @ForeignKey(name = "fk_booking_flight")
    )
    private Flight flight;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "passenger_id",
            nullable = false,
            foreignKey = @ForeignKey(name = "fk_booking_passenger")
    )
    private Passenger passenger;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "seat_id",
            foreignKey = @ForeignKey(name = "fk_booking_seat")
    )
    private Seat seat;

    @Column(name = "refund_amount", precision = 10, scale = 2)
    private BigDecimal refundAmount;

    @Column(name = "refund_processed_at")
    private LocalDateTime refundProcessedAt;
}