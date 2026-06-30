package com.crm.service.impl;

import com.crm.dto.DashboardSummaryDto;
import com.crm.repository.CustomerRepository;
import com.crm.repository.LeadRepository;
import com.crm.repository.TaskRepository;
import com.crm.service.DashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

/**
 * This is a Service layer class. It contains the core 'business logic' of our application. Controllers call services to fetch or modify data, and services interact with repositories to save those changes.
 */
@Service
@RequiredArgsConstructor
public class DashboardServiceImpl implements DashboardService {

    private final CustomerRepository customerRepository;
    private final LeadRepository leadRepository;
    private final TaskRepository taskRepository;

    @Override
    public DashboardSummaryDto getDashboardSummary() {
        long totalCustomers = customerRepository.count();
        
        long openLeads = leadRepository.findAll().stream()
                .filter(lead -> lead.getStatus() != "CLOSED_WON" && lead.getStatus() != "CLOSED_LOST")
                .count();

        LocalDate today = LocalDate.now();
        LocalDate nextWeek = today.plusDays(7);

        long overdueTasks = taskRepository.findByDueDateBeforeAndStatusNot(today, "COMPLETED").size();
        
        long upcomingFollowUps = taskRepository.findByDueDateBetweenAndStatusNot(today, nextWeek, "COMPLETED").size();

        return DashboardSummaryDto.builder()
                .totalCustomers(totalCustomers)
                .openLeads(openLeads)
                .overdueTasks(overdueTasks)
                .upcomingFollowUps(upcomingFollowUps)
                .build();
    }
}
