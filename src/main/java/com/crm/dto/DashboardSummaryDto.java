package com.crm.dto;

import lombok.Builder;
import lombok.Data;

/**
 * This is a Data Transfer Object (DTO). Instead of sending direct database entities to the frontend (which is insecure and can cause errors), we safely map data into this temporary object before sending it to the user's browser.
 */
@Data
@Builder
public class DashboardSummaryDto {
    private long totalCustomers;
    private long openLeads;
    private long overdueTasks;
    private long upcomingFollowUps;
}
