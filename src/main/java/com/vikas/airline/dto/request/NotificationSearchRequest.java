package com.vikas.airline.dto.request;

import com.vikas.airline.enums.NotificationStatus;
import com.vikas.airline.enums.NotificationType;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NotificationSearchRequest {

    private Long bookingId;

    private Long userId;

    private String recipientEmail;

    private String subject;

    private NotificationType type;

    private NotificationStatus status;

    private LocalDateTime sentFrom;

    private LocalDateTime sentTo;

    private LocalDateTime createdFrom;

    private LocalDateTime createdTo;
}