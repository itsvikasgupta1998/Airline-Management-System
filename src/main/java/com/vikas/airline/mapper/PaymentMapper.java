package com.vikas.airline.mapper;

import com.vikas.airline.dto.response.PaymentResponse;
import com.vikas.airline.entity.Payment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PaymentMapper {

    @Mapping(target = "bookingId", source = "booking.id")
    PaymentResponse toResponse(Payment payment);
}