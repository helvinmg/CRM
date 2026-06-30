package com.crm.repository;

import com.crm.entity.LeadStatusHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LeadStatusHistoryRepository extends JpaRepository<LeadStatusHistory, Long> {
    
    // Spring Data JPA will automatically generate the SQL to fetch history for a specific lead, ordered by date descending
    List<LeadStatusHistory> findByLead_LeadIdOrderByChangeDateDesc(Long leadId);
}
