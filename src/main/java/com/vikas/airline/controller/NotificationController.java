package com.vikas.airline.controller;

import com.vikas.airline.dto.request.CreateNotificationRequest;
import com.vikas.airline.dto.request.NotificationSearchRequest;
import com.vikas.airline.dto.request.UpdateNotificationRequest;
import com.vikas.airline.dto.response.ApiResponse;
import com.vikas.airline.dto.response.NotificationResponse;
import com.vikas.airline.service.NotificationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/notifications")
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService notificationService;

    @PostMapping
    public ResponseEntity<ApiResponse<NotificationResponse>> createNotification(

            @Valid
            @RequestBody
            CreateNotificationRequest request) {

        return ResponseEntity.ok(ApiResponse.success(
                "Notification created successfully.",
                notificationService.createNotification(request)));
    }


    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<NotificationResponse>> updateNotification(
            @PathVariable Long id,
            @Valid @RequestBody UpdateNotificationRequest request) {

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Notification updated successfully.",
                        notificationService.updateNotification(id, request)
                )
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<NotificationResponse>> getNotificationById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Notification retrieved successfully.",
                        notificationService.getNotificationById(id)
                )
        );
    }

    @GetMapping
    public ResponseEntity<ApiResponse<Page<NotificationResponse>>> getAllNotifications(

            @RequestParam(defaultValue = "0")
            int page,

            @RequestParam(defaultValue = "10")
            int size,

            @RequestParam(defaultValue = "id")
            String sortBy,

            @RequestParam(defaultValue = "desc")
            String sortDir) {

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Notifications retrieved successfully.",
                        notificationService.getAllNotifications(
                                page,
                                size,
                                sortBy,
                                sortDir)
                )
        );
    }

    @GetMapping("/search")
    public ResponseEntity<ApiResponse<Page<NotificationResponse>>> searchNotifications(

            NotificationSearchRequest request,

            @RequestParam(defaultValue = "0")
            int page,

            @RequestParam(defaultValue = "10")
            int size,

            @RequestParam(defaultValue = "id")
            String sortBy,

            @RequestParam(defaultValue = "desc")
            String sortDir) {

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Notifications retrieved successfully.",
                        notificationService.searchNotifications(
                                request,
                                page,
                                size,
                                sortBy,
                                sortDir)
                )
        );
    }

    @PostMapping("/{id}/send")
    public ResponseEntity<ApiResponse<Void>> sendNotification(
            @PathVariable Long id) {

        notificationService.sendNotification(id);

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Notification sent successfully.",
                        null)
        );
    }

    @PostMapping("/{id}/read")
    public ResponseEntity<ApiResponse<Void>> markAsRead(
            @PathVariable Long id) {

        notificationService.markAsRead(id);

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Notification marked as read.",
                        null)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteNotification(
            @PathVariable Long id) {

        notificationService.deleteNotification(id);

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Notification deleted successfully.",
                        null)
        );
    }

    @PatchMapping("/{id}/restore")
    public ResponseEntity<ApiResponse<Void>> restoreNotification(
            @PathVariable Long id) {

        notificationService.restoreNotification(id);

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Notification restored successfully.",
                        null)
        );
    }
}