package com.vikas.airline.entity;

import com.vikas.airline.enums.NotificationStatus;
import com.vikas.airline.enums.NotificationType;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(
        name = "notifications",
        indexes = {

                @Index(
                        name = "idx_notification_user",
                        columnList = "user_id"),

                @Index(
                        name = "idx_notification_booking",
                        columnList = "booking_id"),

                @Index(
                        name = "idx_notification_status",
                        columnList = "notification_status"),

                @Index(
                        name = "idx_notification_type",
                        columnList = "notification_type"),

                @Index(
                        name = "idx_notification_sent_at",
                        columnList = "sent_at")
        }
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Notification extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "user_id",
            nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "booking_id")
    private Booking booking;

    @Enumerated(EnumType.STRING)
    @Column(
            name = "notification_type",
            nullable = false,
            length = 20)
    private NotificationType notificationType;

    @Enumerated(EnumType.STRING)
    @Column(
            name = "notification_status",
            nullable = false,
            length = 20)
    private NotificationStatus notificationStatus;

    @Column(
            nullable = false,
            length = 200)
    private String subject;

    @Lob
    @Column(
            nullable = false,
            columnDefinition = "TEXT")
    private String message;

    @Column(
            name = "recipient_email",
            length = 150)
    private String recipientEmail;

    @Column(
            name = "recipient_mobile",
            length = 20)
    private String recipientMobile;

    @Column(
            name = "sent_at")
    private LocalDateTime sentAt;

    @Column(name = "read_at")
    private LocalDateTime readAt;

    @Column(
            name = "failure_reason",
            length = 500)
    private String failureReason;

    @Builder.Default
    @Column(
            name = "retry_count",
            nullable = false)
    private Integer retryCount = 0;

    @Builder.Default
    @Column(nullable = false)
    private Boolean active = true;
}