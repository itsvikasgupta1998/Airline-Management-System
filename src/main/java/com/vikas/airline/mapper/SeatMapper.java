package com.vikas.airline.mapper;

import com.vikas.airline.dto.response.SeatResponse;
import com.vikas.airline.entity.Seat;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface SeatMapper {

    SeatResponse toResponse(Seat seat);

    List<SeatResponse> toResponseList(List<Seat> seats);
}