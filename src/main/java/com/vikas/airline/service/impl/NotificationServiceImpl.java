package com.vikas.airline.service.impl;

import com.vikas.airline.dto.request.CreateNotificationRequest;
import com.vikas.airline.dto.request.NotificationSearchRequest;
import com.vikas.airline.dto.request.UpdateNotificationRequest;
import com.vikas.airline.dto.response.NotificationResponse;
import com.vikas.airline.entity.Booking;
import com.vikas.airline.entity.Notification;
import com.vikas.airline.entity.User;
import com.vikas.airline.enums.NotificationStatus;
import com.vikas.airline.enums.NotificationType;
import com.vikas.airline.exception.BadRequestException;
import com.vikas.airline.exception.ResourceNotFoundException;
import com.vikas.airline.mapper.NotificationMapper;
import com.vikas.airline.repository.BookingRepository;
import com.vikas.airline.repository.NotificationRepository;
import com.vikas.airline.repository.UserRepository;
import com.vikas.airline.service.EmailService;
import com.vikas.airline.service.NotificationService;
import com.vikas.airline.specification.NotificationSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;


@Service
@RequiredArgsConstructor
@Transactional
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository notificationRepository;
    private final UserRepository userRepository;
    private final BookingRepository bookingRepository;
    private final NotificationMapper notificationMapper;
    private final EmailService emailService;

    @Override
    public NotificationResponse createNotification(CreateNotificationRequest request) {

        Notification notification = notificationMapper.toEntity(request);
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "User not found with id: " + request.getUserId()));

        if (request.getBookingId() != null) {

            Booking booking = bookingRepository
                    .findByIdAndActiveTrue(request.getBookingId())
                    .orElseThrow(() ->
                            new ResourceNotFoundException(
                                    "Booking not found with id: " + request.getBookingId()));

            notification.setBooking(booking);
        }

        notification.setUser(user);
        notification.setNotificationStatus(NotificationStatus.PENDING);
        notification.setSentAt(null);
        notification.setReadAt(null);
        notification.setRetryCount(0);
        notification.setFailureReason(null);
        notification.setActive(true);

        Notification saved = notificationRepository.save(notification);
        return notificationMapper.toResponse(saved);
    }


    @Override
    @Transactional(readOnly = true)
    public NotificationResponse getNotificationById(Long id) {

        Notification notification = getActiveNotification(id);

        return notificationMapper.toResponse(notification);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<NotificationResponse> getAllNotifications(
            int page,
            int size,
            String sortBy,
            String sortDir) {

        validatePagination(page, size);
        validateSortField(sortBy);

        Pageable pageable =
                buildPageable(page, size, sortBy, sortDir);

        return notificationRepository
                .findAllByActiveTrue(pageable)
                .map(notificationMapper::toResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<NotificationResponse> searchNotifications(
            NotificationSearchRequest request,
            int page,
            int size,
            String sortBy,
            String sortDir) {

        validatePagination(page, size);
        validateSortField(sortBy);

        Pageable pageable =
                buildPageable(page, size, sortBy, sortDir);

        return notificationRepository.findAll(
                        NotificationSpecification.search(request),
                        pageable)
                .map(notificationMapper::toResponse);
    }

    @Override
    public NotificationResponse updateNotification(
            Long id,
            UpdateNotificationRequest request) {

        Notification notification =
                getActiveNotification(id);

        validateEditable(notification);

        if (request.getSubject() != null) {
            notification.setSubject(request.getSubject().trim());
        }

        if (request.getMessage() != null) {
            notification.setMessage(request.getMessage().trim());
        }

        Notification updated =
                notificationRepository.save(notification);

        return notificationMapper.toResponse(updated);
    }

    @Override
    public void deleteNotification(Long id) {

        Notification notification = getNotification(id);

        if (!notification.getActive()) {

            throw new BadRequestException(
                    "Notification is already deleted.");
        }

        notification.setActive(false);

        notificationRepository.save(notification);
    }

    @Override
    public void restoreNotification(Long id) {

        Notification notification = getNotification(id);

        if (notification.getActive()) {

            throw new BadRequestException(
                    "Notification is already active.");
        }

        notification.setActive(true);

        notificationRepository.save(notification);
    }

    @Override
    public void markAsRead(Long id) {
        Notification notification = getActiveNotification(id);
        notification.setNotificationStatus(NotificationStatus.READ);
        notification.setReadAt(LocalDateTime.now());
        notificationRepository.save(notification);
    }

    @Override
    @Transactional
    public void sendNotification(Long id) {

        Notification notification = notificationRepository
                .findByIdAndActiveTrue(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Notification not found with id: "
                                        +id));

        if (notification.getNotificationStatus() == NotificationStatus.SENT) {
            throw new BadRequestException("Notification has already been sent.");
        }

        try {

            if (Objects.requireNonNull(notification.getNotificationType())
                    == NotificationType.EMAIL) {
                emailService.sendHtmlMail(

                        notification.getRecipientEmail(),
                        notification.getSubject(),
                        notification.getMessage());

            }
            else {
                throw new BadRequestException("Unsupported notification type.");
            }

            notification.setNotificationStatus(NotificationStatus.SENT);
            notification.setSentAt(LocalDateTime.now());

        }
        catch (Exception ex) {
            notification.setNotificationStatus(NotificationStatus.FAILED);
            throw ex;
        }

        notificationRepository.save(notification);
    }

    @Override
    @Transactional
    public void createAndSendNotification(
            Long userId,
            Long bookingId,
            String recipient,
            String subject,
            String message,
            NotificationType notificationType) {

        User user = userRepository.findById(userId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "User not found with id: " + userId));

        Notification notification = Notification.builder()
                .user(user)
                .recipientEmail(recipient)
                .subject(subject)
                .message(message)
                .notificationType(notificationType)
                .notificationStatus(NotificationStatus.PENDING)
                .retryCount(0)
                .active(true)
                .build();

        if (bookingId != null) {

            notification.setBooking(

                    bookingRepository
                            .findByIdAndActiveTrue(bookingId)
                            .orElseThrow(() ->
                                    new ResourceNotFoundException(
                                            "Booking not found with id: " + bookingId))
            );
        }

        Notification savedNotification =
                notificationRepository.save(notification);

        sendNotification(savedNotification.getId());
    }



    // ===========================
    // Helper Methods
    // ===========================

    private Notification getNotification(Long id) {

        return notificationRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Notification not found with id: " + id));
    }

    private Notification getActiveNotification(Long id) {

        return notificationRepository.findByIdAndActiveTrue(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Notification not found with id: " + id));
    }

    private void validateEditable(Notification notification) {

        if (notification.getNotificationStatus() == NotificationStatus.SENT
                || notification.getNotificationStatus() == NotificationStatus.READ) {

            throw new BadRequestException(
                    "Sent notification cannot be updated.");
        }
    }

    private Pageable buildPageable(
            int page,
            int size,
            String sortBy,
            String sortDir) {

        Sort sort = sortDir.equalsIgnoreCase("desc")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();

        return PageRequest.of(page, size, sort);
    }

    private void validatePagination(
            int page,
            int size) {

        if (page < 0) {

            throw new BadRequestException(
                    "Page cannot be negative.");
        }

        if (size <= 0) {

            throw new BadRequestException(
                    "Size must be greater than zero.");
        }

        if (size > 100) {

            throw new BadRequestException(
                    "Maximum page size is 100.");
        }
    }

    private void validateSortField(String sortBy) {

        List<String> allowedFields = List.of(
                "id",
                "subject",
                "status",
                "type",
                "sentAt",
                "createdAt"
        );

        if (!allowedFields.contains(sortBy)) {

            throw new BadRequestException(
                    "Invalid sort field: " + sortBy);
        }
    }
}