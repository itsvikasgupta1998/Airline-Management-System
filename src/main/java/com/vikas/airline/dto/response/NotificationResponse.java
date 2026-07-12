package com.vikas.airline.dto.response;

import com.vikas.airline.enums.NotificationStatus;
import com.vikas.airline.enums.NotificationType;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NotificationResponse {

    private Long id;
    private Long bookingId;
    private Long userId;
    private String recipientEmail;
    private String subject;
    private String message;
    private NotificationType type;
    private NotificationStatus status;
    private LocalDateTime sentAt;
    private LocalDateTime readAt;
    private Boolean active;
    private LocalDateTime createdAt;

}