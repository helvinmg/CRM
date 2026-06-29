package com.crm.service;

import com.crm.dto.UserDto;
import com.crm.entity.User;
import java.util.List;

public interface UserService {
    List<UserDto> getAllUsers();
    UserDto createUser(User user);
    UserDto updateUser(Long id, User user);
    void deactivateUser(Long id);
    User findByEmail(String email);
}
