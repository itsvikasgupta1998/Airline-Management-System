package com.vikas.airline.mapper;

import com.vikas.airline.dto.request.RegisterRequest;
import com.vikas.airline.dto.request.UpdateUserRequest;
import com.vikas.airline.dto.response.UserResponse;
import com.vikas.airline.entity.User;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "password", ignore = true)
    @Mapping(target = "active", constant = "true")
    User toEntity(RegisterRequest request);



    UserResponse toResponse(User user);

    @BeanMapping(
            nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
    )
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "email", ignore = true)
    @Mapping(target = "password", ignore = true)
    @Mapping(target = "role", ignore = true)
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    void updateUserFromRequest(
            UpdateUserRequest request,
            @MappingTarget User user
    );


}