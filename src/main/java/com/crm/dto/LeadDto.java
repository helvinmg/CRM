package com.crm.dto;

import com.crm.enums.LeadStatus;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class LeadDto {
    private Long leadId;
    private Long customerId;
    private String customerName;
    private Long assignedTo;
    private String assignedUserName;
    private LeadStatus status;
    private String source;
    private LocalDate expectedCloseDate;
}
