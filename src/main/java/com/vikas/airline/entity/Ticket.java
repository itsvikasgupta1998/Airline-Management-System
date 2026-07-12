package com.vikas.airline.entity;

import com.vikas.airline.enums.TicketStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(
        name = "tickets",
        uniqueConstraints = {

                @UniqueConstraint(
                        name = "uk_ticket_number",
                        columnNames = "ticket_number"
                )
        },
        indexes = {

                @Index(
                        name = "idx_ticket_number",
                        columnList = "ticket_number"
                ),

                @Index(
                        name = "idx_ticket_status",
                        columnList = "ticket_status"
                ),

                @Index(
                        name = "idx_ticket_booking",
                        columnList = "booking_id"
                ),

                @Index(
                        name = "idx_ticket_issued_at",
                        columnList = "issued_at"
                )
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Ticket extends BaseEntity {

    @Column(
            name = "ticket_number",
            nullable = false,
            unique = true,
            length = 25
    )
    private String ticketNumber;

    @Enumerated(EnumType.STRING)
    @Column(
            name = "ticket_status",
            nullable = false,
            length = 20
    )
    private TicketStatus ticketStatus;

    @Column(
            name = "issued_at",
            nullable = false
    )
    private LocalDateTime issuedAt;

    @Column(name = "cancelled_at")
    private LocalDateTime cancelledAt;

    @Column(name = "qr_code")
    private String qrCode;

    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Column(name = "pdf_file",columnDefinition = "LONGBLOB")
    private byte[] pdfFile;

    @Column(length = 500)
    private String remarks;

    @Column(nullable = false)
    @Builder.Default
    private Boolean active = true;

    // =====================================
    // Relationship
    // =====================================

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "booking_id",
            nullable = false,
            unique = true,
            foreignKey = @ForeignKey(name = "fk_ticket_booking")
    )
    private Booking booking;
}