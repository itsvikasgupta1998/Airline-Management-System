package com.vikas.airline.dto.request;

import com.vikas.airline.enums.NotificationType;
import jakarta.validation.constraints.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateNotificationRequest {

    @NotNull(message = "User Id is required.")
    private Long userId;

    private Long bookingId;

    @NotNull(message = "Notification type is required.")
    private NotificationType notificationType;

    @NotBlank(message = "Subject is required.")
    @Size(max = 200)
    private String subject;

    @NotBlank(message = "Message is required.")
    @Size(max = 5000)
    private String message;

    @Email(message = "Invalid email.")
    @Size(max = 150)
    private String recipientEmail;

    @Size(max = 20)
    private String recipientMobile;
}