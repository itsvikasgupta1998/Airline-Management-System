package com.vikas.airline.controller;

import com.vikas.airline.dto.request.ChangePasswordRequest;
import com.vikas.airline.dto.request.UpdateUserRequest;
import com.vikas.airline.dto.request.UpdateUserRoleRequest;
import com.vikas.airline.dto.response.ApiResponse;
import com.vikas.airline.dto.response.UserResponse;
import com.vikas.airline.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/me")
    public ResponseEntity<ApiResponse<UserResponse>> getCurrentUser() {

        return ResponseEntity.ok(
                ApiResponse.success(
                        "User profile fetched successfully.",
                        userService.getCurrentUser()
                )
        );
    }

    @PutMapping("/me")
    public ResponseEntity<ApiResponse<UserResponse>> updateCurrentUser(
            @Valid @RequestBody UpdateUserRequest request
    ) {

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Profile updated successfully.",
                        userService.updateCurrentUser(request)
                )
        );
    }

    @PutMapping("/me/change-password")
    public ResponseEntity<ApiResponse<Void>> changePassword(
            @Valid @RequestBody ChangePasswordRequest request
    ) {

        userService.changePassword(request);

        return ResponseEntity.ok(
                ApiResponse.success("Password changed successfully.")
        );
    }

    @PatchMapping("/{userId}/role")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<UserResponse>> updateRole(
            @PathVariable Long userId,
            @Valid @RequestBody UpdateUserRoleRequest request
    ) {

        return ResponseEntity.ok(
                ApiResponse.success("Role updated successfully",
                        userService.updateUserRole(
                                userId,
                                request
                        )
                )
        );
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{userId}")
    public ResponseEntity<ApiResponse<UserResponse>> getUserById(
            @PathVariable Long userId
    ) {

        return ResponseEntity.ok(
                ApiResponse.success(
                        "User fetched successfully.",
                        userService.getUserById(userId)
                )
        );
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{userId}")
    public ResponseEntity<ApiResponse<Void>> deleteUser(
            @PathVariable Long userId
    ) {

        userService.deleteUser(userId);

        return ResponseEntity.ok(
                ApiResponse.success("User deleted successfully.")
        );
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("/{userId}/restore")
    public ResponseEntity<ApiResponse<Void>> restoreUser(
            @PathVariable Long userId
    ) {

        userService.restoreUser(userId);
        return ResponseEntity.ok(ApiResponse.success("User restored successfully."));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<ApiResponse<Page<UserResponse>>> getAllUsers(

            @RequestParam(defaultValue = "0") int page,

            @RequestParam(defaultValue = "10") int size,

            @RequestParam(defaultValue = "id") String sortBy,

            @RequestParam(defaultValue = "asc") String sortDir
    ) {

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Users fetched successfully.",
                        userService.getAllUsers(
                                page,
                                size,
                                sortBy,
                                sortDir
                        )
                )
        );
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/deleted")
    public ResponseEntity<ApiResponse<Page<UserResponse>>> getDeletedUsers(

            @RequestParam(defaultValue = "0") int page,

            @RequestParam(defaultValue = "10") int size,

            @RequestParam(defaultValue = "id") String sortBy,

            @RequestParam(defaultValue = "asc") String sortDir
    ) {

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Deleted users fetched successfully.",
                        userService.getDeletedUsers(
                                page,
                                size,
                                sortBy,
                                sortDir
                        )
                )
        );
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/all")
    public ResponseEntity<ApiResponse<Page<UserResponse>>> getAllUsersIncludingDeleted(

            @RequestParam(defaultValue = "0") int page,

            @RequestParam(defaultValue = "10") int size,

            @RequestParam(defaultValue = "id") String sortBy,

            @RequestParam(defaultValue = "asc") String sortDir
    ) {

        return ResponseEntity.ok(
                ApiResponse.success(
                        "All users fetched successfully.",
                        userService.getAllUsersIncludingDeleted(
                                page,
                                size,
                                sortBy,
                                sortDir
                        )
                )
        );
    }

}