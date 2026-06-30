package com.crm.service;

import com.crm.dto.UserDto;
import com.crm.entity.User;
import java.util.List;

/**
 * This is a Service layer class. It contains the core 'business logic' of our application. Controllers call services to fetch or modify data, and services interact with repositories to save those changes.
 */
public interface UserService {
    List<UserDto> getAllUsers();
    UserDto createUser(User user);
    UserDto updateUser(Long id, User user);
    void deactivateUser(Long id);
    User findByEmail(String email);
    void updateUserProfile(String email, String fullName);
    void changePassword(String email, String newPassword);
}
