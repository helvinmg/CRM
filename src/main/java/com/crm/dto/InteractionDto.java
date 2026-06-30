package com.crm.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

/**
 * This is a Data Transfer Object (DTO). Instead of sending direct database entities to the frontend (which is insecure and can cause errors), we safely map data into this temporary object before sending it to the user's browser.
 */
@Data
@Builder
public class InteractionDto {
    private Long interactionId;
    private Long leadId;
    private Long userId;
    private String userName;
    private String interactionType;
    private String subject;
    private String notes;
    private LocalDate interactionDate;
}
