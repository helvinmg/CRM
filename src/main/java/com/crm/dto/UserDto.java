package com.crm.dto;

import com.crm.enums.UserRole;
import com.crm.enums.UserStatus;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserDto {
    private Long userId;
    private String fullName;
    private String email;
    private UserRole role;
    private UserStatus status;
}
