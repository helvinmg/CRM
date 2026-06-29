package com.crm.service;

import com.crm.dto.InteractionDto;
import java.util.List;

public interface InteractionService {
    List<InteractionDto> getAllInteractions();
    List<InteractionDto> getInteractionsByLead(Long leadId);
    InteractionDto createInteraction(InteractionDto interactionDto);
    InteractionDto updateInteraction(Long id, InteractionDto interactionDto);
    void deleteInteraction(Long id);
}
