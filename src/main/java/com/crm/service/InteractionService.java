package com.crm.service;

import com.crm.dto.InteractionDto;
import java.util.List;

/**
 * This is a Service layer class. It contains the core 'business logic' of our application. Controllers call services to fetch or modify data, and services interact with repositories to save those changes.
 */
public interface InteractionService {
    List<InteractionDto> getAllInteractions();
    List<InteractionDto> getInteractionsByLead(Long leadId);
    InteractionDto createInteraction(InteractionDto interactionDto);
    InteractionDto updateInteraction(Long id, InteractionDto interactionDto);
    void deleteInteraction(Long id);
}
