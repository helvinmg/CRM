package com.crm.service;

import com.crm.dto.LeadDto;
import com.crm.enums.LeadStatus;
import java.util.List;

public interface LeadService {
    List<LeadDto> getAllLeads();
    List<LeadDto> getLeadsByUser(Long userId);
    LeadDto getLeadById(Long id);
    LeadDto createLead(LeadDto leadDto);
    LeadDto updateLead(Long id, LeadDto leadDto);
    void deleteLead(Long id);
    LeadDto updateLeadStatus(Long id, LeadStatus status);
}
