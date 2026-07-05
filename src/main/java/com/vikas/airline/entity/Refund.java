package com.vikas.airline.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(
        name = "refund",
        indexes = {
                @Index(
                        name = "idx_refund_booking",
                        columnList = "booking_id"
                )
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Refund extends BaseEntity {

    @Column(
            nullable = false,
            precision = 10,
            scale = 2
    )
    private BigDecimal amount;

    @Column(name = "refund_time", nullable = false)
    private LocalDateTime refundTime;

    @NotNull
    private String reason;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "booking_id",
            nullable = false,
            unique = true
    )
    private Booking booking;

}