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
        uniqueConstraints = {

                @UniqueConstraint(
                        name = "uk_payment_transaction_id",
                        columnNames = "transaction_id"
                ),

                @UniqueConstraint(
                        name = "uk_payment_gateway_reference",
                        columnNames = "gateway_reference"
                )
        },
        indexes = {

                @Index(
                        name = "idx_payment_booking",
                        columnList = "booking_id"
                ),

                @Index(
                        name = "idx_payment_status",
                        columnList = "payment_status"
                ),

                @Index(
                        name = "idx_payment_method",
                        columnList = "payment_method"
                ),

                @Index(
                        name = "idx_payment_transaction",
                        columnList = "transaction_id"
                ),

                @Index(
                        name = "idx_payment_gateway_reference",
                        columnList = "gateway_reference"
                ),

                @Index(
                        name = "idx_payment_paid_at",
                        columnList = "paid_at"
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
            name = "transaction_id",
            nullable = false,
            unique = true,
            length = 50
    )
    private String transactionId;

    @Column(
            name = "gateway_reference",
            unique = true,
            length = 100
    )
    private String gatewayReference;

    @Enumerated(EnumType.STRING)
    @Column(
            name = "payment_method",
            nullable = false,
            length = 30
    )
    private PaymentMethod paymentMethod;

    @Enumerated(EnumType.STRING)
    @Column(
            name = "payment_status",
            nullable = false,
            length = 30
    )
    private PaymentStatus paymentStatus;

    @Column(
            nullable = false,
            precision = 10,
            scale = 2
    )
    private BigDecimal amount;

    @Column(
            nullable = false,
            length = 3
    )
    @Builder.Default
    private String currency = "INR";

    @Column(name = "paid_at")
    private LocalDateTime paidAt;

    @Column(name = "refund_amount",
            precision = 10,
            scale = 2)
    @Builder.Default
    private BigDecimal refundAmount = BigDecimal.ZERO;

    @Column(name = "refund_processed_at")
    private LocalDateTime refundProcessedAt;

    @Column(
            name = "refund_reason",
            length = 500)
    private String refundReason;

    @Column(length = 500)
    private String remarks;

    @Column(nullable = false)
    @Builder.Default
    private Boolean active = true;

    // ===============================
    // Relationships
    // ===============================

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "booking_id",
            nullable = false,
            foreignKey = @ForeignKey(name = "fk_payment_booking")
    )
    private Booking booking;
}