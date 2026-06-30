package com.crm.service.impl;

import com.crm.dto.LeadDto;
import com.crm.entity.Customer;
import com.crm.entity.Lead;
import com.crm.entity.User;
import com.crm.exception.ResourceNotFoundException;
import com.crm.repository.CustomerRepository;
import com.crm.repository.LeadRepository;
import com.crm.repository.UserRepository;
import com.crm.service.LeadService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * This is a Service layer class. It contains the core 'business logic' of our application. Controllers call services to fetch or modify data, and services interact with repositories to save those changes.
 */
@Service
@RequiredArgsConstructor
public class LeadServiceImpl implements LeadService {

    private final LeadRepository leadRepository;
    private final CustomerRepository customerRepository;
    private final UserRepository userRepository;
    private final com.crm.repository.LeadStatusHistoryRepository leadStatusHistoryRepository;

    @Override
    public List<LeadDto> getAllLeads() {
        return leadRepository.findAll().stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<LeadDto> getLeadsByUser(Long userId) {
        return leadRepository.findByAssignedTo_UserId(userId).stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public LeadDto getLeadById(Long id) {
        return leadRepository.findById(id)
                .map(this::mapToDto)
                .orElseThrow(() -> new ResourceNotFoundException("Lead not found"));
    }

    @Override
    @Transactional
    public LeadDto createLead(LeadDto leadDto) {
        Customer customer = customerRepository.findById(leadDto.getCustomerId())
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found"));
        User user = null;
        if (leadDto.getAssignedTo() != null) {
            user = userRepository.findById(leadDto.getAssignedTo())
                    .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        }

        Lead lead = Lead.builder()
                .customer(customer)
                .assignedTo(user)
                .title(leadDto.getTitle())
                .status(leadDto.getStatus())
                .source(leadDto.getSource())
                .expectedCloseDate(leadDto.getExpectedCloseDate())
                .build();
        return mapToDto(leadRepository.save(lead));
    }

    @Override
    @Transactional
    public LeadDto updateLead(Long id, LeadDto leadDto) {
        Lead lead = leadRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Lead not found"));
        
        if (leadDto.getAssignedTo() != null) {
            User user = userRepository.findById(leadDto.getAssignedTo())
                    .orElseThrow(() -> new ResourceNotFoundException("User not found"));
            lead.setAssignedTo(user);
        }
        
        lead.setTitle(leadDto.getTitle());
        lead.setStatus(leadDto.getStatus());
        lead.setSource(leadDto.getSource());
        lead.setExpectedCloseDate(leadDto.getExpectedCloseDate());
        return mapToDto(leadRepository.save(lead));
    }

    @Override
    @Transactional
    public void deleteLead(Long id) {
        Lead lead = leadRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Lead not found"));
        leadRepository.delete(lead);
    }

    @Override
    @Transactional
    public LeadDto updateLeadStatus(Long id, String status) {
        Lead lead = leadRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Lead not found"));
        
        if (!lead.getStatus().equals(status)) {
            // Log the change
            com.crm.entity.LeadStatusHistory history = com.crm.entity.LeadStatusHistory.builder()
                    .lead(lead)
                    .oldStatus(lead.getStatus())
                    .newStatus(status)
                    // Normally we would get the logged in user here. We hardcode to Admin user ID 1 for now.
                    .changedBy(userRepository.findById(1L).orElse(lead.getAssignedTo()))
                    .build();
            leadStatusHistoryRepository.save(history);
            
            lead.setStatus(status);
            lead = leadRepository.save(lead);
        }
        
        return mapToDto(lead);
    }

    private LeadDto mapToDto(Lead lead) {
        return LeadDto.builder()
                .leadId(lead.getLeadId())
                .customerId(lead.getCustomer().getCustomerId())
                .customerName(lead.getCustomer().getCustomerName())
                .assignedTo(lead.getAssignedTo() != null ? lead.getAssignedTo().getUserId() : null)
                .assignedUserName(lead.getAssignedTo() != null ? lead.getAssignedTo().getFullName() : null)
                .title(lead.getTitle())
                .status(lead.getStatus())
                .source(lead.getSource())
                .expectedCloseDate(lead.getExpectedCloseDate())
                .build();
    }
}
