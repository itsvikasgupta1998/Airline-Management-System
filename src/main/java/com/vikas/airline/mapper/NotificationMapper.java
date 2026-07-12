package com.vikas.airline.mapper;

import com.vikas.airline.dto.request.CreateNotificationRequest;
import com.vikas.airline.dto.response.NotificationResponse;
import com.vikas.airline.entity.Notification;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface NotificationMapper {

    @Mapping(target = "userId", source = "user.id")
    @Mapping(target = "bookingId", source = "booking.id")
    @Mapping(target = "type", source = "notificationType")
    @Mapping(target = "status", source = "notificationStatus")
    NotificationResponse toResponse(Notification notification);


    @Mapping(target = "user", ignore = true)
    @Mapping(target = "booking", ignore = true)
    @Mapping(target = "notificationStatus", ignore = true)
    @Mapping(target = "sentAt", ignore = true)
    @Mapping(target = "readAt", ignore = true)
    @Mapping(target = "failureReason", ignore = true)
    @Mapping(target = "retryCount", ignore = true)
    @Mapping(target = "active", ignore = true)
    Notification toEntity(CreateNotificationRequest request);
}