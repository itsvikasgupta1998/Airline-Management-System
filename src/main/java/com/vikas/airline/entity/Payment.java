package com.vikas.airline.entity;

import com.vikas.airline.enums.PaymentMethod;
import com.vikas.airline.enums.PaymentStatus;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(
        name = "payments",
        indexes = {
                @Index(
                        name = "idx_payment_reference",
                        columnList = "payment_reference"
                ),
                @Index(
                        name = "idx_payment_status",
                        columnList = "status"
                )
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Payment extends BaseEntity {

    @Column(
            name = "payment_reference",
            nullable = false,
            unique = true,
            length = 100
    )
    private String paymentReference;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private PaymentMethod method;

    @Column(
            nullable = false,
            precision = 10,
            scale = 2
    )
    private BigDecimal amount;

    @Column(name = "payment_time", nullable = false)
    private LocalDateTime paymentTime;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private PaymentStatus status;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "booking_id",
            nullable = false,
            unique = true
    )
    private Booking booking;

}