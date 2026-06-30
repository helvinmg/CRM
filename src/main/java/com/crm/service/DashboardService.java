package com.crm.service;

import com.crm.dto.DashboardSummaryDto;

/**
 * This is a Service layer class. It contains the core 'business logic' of our application. Controllers call services to fetch or modify data, and services interact with repositories to save those changes.
 */
public interface DashboardService {
    DashboardSummaryDto getDashboardSummary();
}
