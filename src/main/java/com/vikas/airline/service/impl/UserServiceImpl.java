package com.vikas.airline.service.impl;

import com.vikas.airline.dto.request.ChangePasswordRequest;
import com.vikas.airline.dto.request.UpdateUserRequest;
import com.vikas.airline.dto.request.UpdateUserRoleRequest;
import com.vikas.airline.dto.response.UserResponse;
import com.vikas.airline.entity.User;
import com.vikas.airline.exception.BadRequestException;
import com.vikas.airline.exception.ResourceNotFoundException;
import com.vikas.airline.mapper.UserMapper;
import com.vikas.airline.repository.UserRepository;
import com.vikas.airline.service.UserService;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional(readOnly = true)
    public UserResponse getCurrentUser() {
        return userMapper.toResponse(getAuthenticatedUserEntity());
    }


    @Override
    @Transactional
    public UserResponse updateCurrentUser(
            UpdateUserRequest request
    ) {

        User user = getAuthenticatedUserEntity();

        userMapper.updateUserFromRequest(request, user);

        User updated = userRepository.save(user);

        log.info(
                "User profile updated. UserId={}, Email={}",
                updated.getId(),
                updated.getEmail()
        );

        return userMapper.toResponse(updated);
    }

    @Override
    public void changePassword(ChangePasswordRequest request) {

        User user = getAuthenticatedUserEntity();

        if (!passwordEncoder.matches(request.getOldPassword(), user.getPassword())) {
            throw new BadRequestException("Current password is incorrect.");
        }
        if (!request.getNewPassword().equals(request.getConfirmPassword())) {
            throw new BadRequestException("New password and confirm password do not match.");
        }

        if (passwordEncoder.matches(request.getNewPassword(), user.getPassword())) {
            throw new BadRequestException("New password must be different from current password.");
        }

        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        userRepository.save(user);
        log.info(
                "Password changed. UserId={}, Email={}",
                user.getId(),
                user.getEmail()
        );
    }

    @Override
    @Transactional(readOnly = true)
    public UserResponse getUserById(Long userId) {

        User user = findUserById(userId);
        return userMapper.toResponse(user);
    }

    @Override
    public void deleteUser(Long userId) {

        User user = findUserById(userId);

        if (!user.isActive()) {
            throw new BadRequestException(
                    "User is already deleted."
            );
        }

        user.setActive(false);

        userRepository.save(user);

        log.info(
                "User soft deleted. UserId={}, Email={}",
                user.getId(),
                user.getEmail()
        );
    }

    @Override
    @Transactional(readOnly = true)
    public Page<UserResponse> getAllUsers(

            int page,
            int size,
            String sortBy,
            String sortDir
    ) {

        Sort sort = sortDir.equalsIgnoreCase("desc")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();

        Pageable pageable = PageRequest.of(page, size, sort);

        return userRepository
                .findByActiveTrue(pageable)
                .map(userMapper::toResponse);
    }

    @Override
    public void restoreUser(Long userId) {

        User user = findUserById(userId);

        if (user.isActive()) {
            throw new BadRequestException(
                    "User is already active."
            );
        }

        user.setActive(true);

        userRepository.save(user);

        log.info("User restored : {}", user.getEmail());
    }

    @Override
    @Transactional(readOnly = true)
    public Page<UserResponse> getDeletedUsers(

            int page,
            int size,
            String sortBy,
            String sortDir
    ) {

        Sort sort = sortDir.equalsIgnoreCase("desc")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();

        Pageable pageable =
                PageRequest.of(page, size, sort);

        return userRepository
                .findByActiveFalse(pageable)
                .map(userMapper::toResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<UserResponse> getAllUsersIncludingDeleted(

            int page,
            int size,
            String sortBy,
            String sortDir
    ) {

        Sort sort = sortDir.equalsIgnoreCase("desc")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();

        Pageable pageable =
                PageRequest.of(page, size, sort);

        return userRepository
                .findAll(pageable)
                .map(userMapper::toResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public User getAuthenticatedUserEntity() {

        Authentication authentication = SecurityContextHolder
                .getContext()
                .getAuthentication();

        if (authentication == null
                || !authentication.isAuthenticated()
                || "anonymousUser".equals(authentication.getPrincipal())) {

            throw new BadRequestException("User is not authenticated.");
        }

        String email = authentication.getName();


        User user =  userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User",email));

        if (!user.isActive()) {
            throw new BadRequestException("User account is disabled.");
        }

        return user;
    }

    private User findUserById(Long id){

        return userRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "User",
                                id
                        ));
    }

    @Override
    public UserResponse updateUserRole(
            Long userId,
            UpdateUserRoleRequest request
    ) {

        User user = findUserById(userId);

        userMapper.updateRoleFromRequest(request, user);

        User updated = userRepository.save(user);

        log.info(
                "User role updated. UserId={}, NewRole={}",
                updated.getId(),
                updated.getRole()
        );

        return userMapper.toResponse(updated);
    }

}
