package com.crm.service;

import com.crm.dto.LeadDto;
import java.util.List;

/**
 * This is a Service layer class. It contains the core 'business logic' of our application. Controllers call services to fetch or modify data, and services interact with repositories to save those changes.
 */
public interface LeadService {
    List<LeadDto> getAllLeads();
    List<LeadDto> getLeadsByUser(Long userId);
    LeadDto getLeadById(Long id);
    LeadDto createLead(LeadDto leadDto);
    LeadDto updateLead(Long id, LeadDto leadDto);
    void deleteLead(Long id);
    LeadDto updateLeadStatus(Long id, String status);
}
