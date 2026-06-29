package com.crm.service.impl;

import com.crm.dto.DashboardSummaryDto;
import com.crm.enums.LeadStatus;
import com.crm.enums.TaskStatus;
import com.crm.repository.CustomerRepository;
import com.crm.repository.LeadRepository;
import com.crm.repository.TaskRepository;
import com.crm.service.DashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

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
                .filter(lead -> lead.getStatus() != LeadStatus.CLOSED_WON && lead.getStatus() != LeadStatus.CLOSED_LOST)
                .count();

        LocalDate today = LocalDate.now();
        LocalDate nextWeek = today.plusDays(7);

        long overdueTasks = taskRepository.findByDueDateBeforeAndStatusNot(today, TaskStatus.COMPLETED).size();
        
        long upcomingFollowUps = taskRepository.findByDueDateBetweenAndStatusNot(today, nextWeek, TaskStatus.COMPLETED).size();

        return DashboardSummaryDto.builder()
                .totalCustomers(totalCustomers)
                .openLeads(openLeads)
                .overdueTasks(overdueTasks)
                .upcomingFollowUps(upcomingFollowUps)
                .build();
    }
}
