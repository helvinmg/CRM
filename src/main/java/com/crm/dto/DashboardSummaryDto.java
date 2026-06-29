package com.crm.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DashboardSummaryDto {
    private long totalCustomers;
    private long openLeads;
    private long overdueTasks;
    private long upcomingFollowUps;
}
