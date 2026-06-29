package com.crm.dto;

import com.crm.enums.InteractionType;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class InteractionDto {
    private Long interactionId;
    private Long leadId;
    private Long userId;
    private String userName;
    private InteractionType interactionType;
    private String subject;
    private String notes;
    private LocalDate interactionDate;
}
