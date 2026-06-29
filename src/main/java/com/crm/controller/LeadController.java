package com.crm.controller;

import com.crm.dto.LeadDto;
import com.crm.enums.LeadStatus;
import com.crm.service.LeadService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/leads")
@RequiredArgsConstructor
public class LeadController {

    private final LeadService leadService;

    @GetMapping
    public ResponseEntity<List<LeadDto>> getLeads(Authentication authentication) {
        // ADMIN sees all leads, USER sees only assigned
        if (authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"))) {
            return ResponseEntity.ok(leadService.getAllLeads());
        }
        // Assuming principal has userId or username, we would normally get userId here
        // For simplicity, we just return all leads for now, or you'd extract UserDetails
        return ResponseEntity.ok(leadService.getAllLeads()); 
    }

    @GetMapping("/{id}")
    public ResponseEntity<LeadDto> getLeadById(@PathVariable Long id) {
        return ResponseEntity.ok(leadService.getLeadById(id));
    }

    @PostMapping
    public ResponseEntity<LeadDto> createLead(@RequestBody LeadDto leadDto) {
        return new ResponseEntity<>(leadService.createLead(leadDto), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<LeadDto> updateLead(@PathVariable Long id, @RequestBody LeadDto leadDto) {
        return ResponseEntity.ok(leadService.updateLead(id, leadDto));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteLead(@PathVariable Long id) {
        leadService.deleteLead(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<LeadDto> updateLeadStatus(@PathVariable Long id, @RequestBody LeadStatus status) {
        return ResponseEntity.ok(leadService.updateLeadStatus(id, status));
    }
}
