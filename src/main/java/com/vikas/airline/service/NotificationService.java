package com.vikas.airline.service;

import com.vikas.airline.dto.request.CreateNotificationRequest;
import com.vikas.airline.dto.request.NotificationSearchRequest;
import com.vikas.airline.dto.request.UpdateNotificationRequest;
import com.vikas.airline.dto.response.NotificationResponse;
import com.vikas.airline.enums.NotificationType;
import org.springframework.data.domain.Page;

public interface NotificationService {

    NotificationResponse createNotification(CreateNotificationRequest request);

    NotificationResponse getNotificationById(Long id);

    Page<NotificationResponse> getAllNotifications(
            int page,
            int size,
            String sortBy,
            String sortDir);

    Page<NotificationResponse> searchNotifications(
            NotificationSearchRequest request,
            int page,
            int size,
            String sortBy,
            String sortDir);

    NotificationResponse updateNotification(
            Long id,
            UpdateNotificationRequest request);

    void deleteNotification(Long id);
    void restoreNotification(Long id);
    void markAsRead(Long id);
    void sendNotification(Long id);
    void createAndSendNotification(
            Long userId,
            Long bookingId,
            String recipient,
            String subject,
            String message,
            NotificationType notificationType);
}