package com.crm.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

/**
 * This is a Data Transfer Object (DTO). Instead of sending direct database entities to the frontend (which is insecure and can cause errors), we safely map data into this temporary object before sending it to the user's browser.
 */
@Data
@Builder
public class CustomerDto {
    private Long customerId;
    
    @NotBlank(message = "Customer name is required")
    private String customerName;
    
    @Email(message = "Invalid email format")
    private String email;
    
    private String phone;
    private String companyName;
}
