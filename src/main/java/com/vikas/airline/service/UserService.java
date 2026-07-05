package com.vikas.airline.service;

import com.vikas.airline.dto.request.ChangePasswordRequest;
import com.vikas.airline.dto.request.UpdateUserRequest;
import com.vikas.airline.dto.response.UserResponse;
import com.vikas.airline.entity.User;
import org.springframework.data.domain.Page;

public interface UserService {

    UserResponse getCurrentUser();

    UserResponse updateCurrentUser(UpdateUserRequest request);

    void changePassword(ChangePasswordRequest request);

    UserResponse getUserById(Long userId);

    void deleteUser(Long userId);

    Page<UserResponse> getAllUsers(
            int page,
            int size,
            String sortBy,
            String sortDir
    );

    void restoreUser(Long userId);

    Page<UserResponse> getDeletedUsers(
            int page,
            int size,
            String sortBy,
            String sortDir
    );

    Page<UserResponse> getAllUsersIncludingDeleted(
            int page,
            int size,
            String sortBy,
            String sortDir);

    User getAuthenticatedUserEntity();


}

