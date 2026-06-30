package com.crm.service.impl;

import com.crm.dto.InteractionDto;
import com.crm.entity.Interaction;
import com.crm.entity.Lead;
import com.crm.entity.User;
import com.crm.exception.ResourceNotFoundException;
import com.crm.repository.InteractionRepository;
import com.crm.repository.LeadRepository;
import com.crm.repository.UserRepository;
import com.crm.service.InteractionService;
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
public class InteractionServiceImpl implements InteractionService {

    private final InteractionRepository interactionRepository;
    private final LeadRepository leadRepository;
    private final UserRepository userRepository;

    @Override
    public List<InteractionDto> getAllInteractions() {
        return interactionRepository.findAll().stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<InteractionDto> getInteractionsByLead(Long leadId) {
        return interactionRepository.findByLead_LeadId(leadId).stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public InteractionDto createInteraction(InteractionDto interactionDto) {
        Lead lead = leadRepository.findById(interactionDto.getLeadId())
                .orElseThrow(() -> new ResourceNotFoundException("Lead not found"));
        User user = userRepository.findById(interactionDto.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        Interaction interaction = Interaction.builder()
                .lead(lead)
                .user(user)
                .interactionType(interactionDto.getInteractionType())
                .subject(interactionDto.getSubject())
                .notes(interactionDto.getNotes())
                .interactionDate(interactionDto.getInteractionDate())
                .build();
        return mapToDto(interactionRepository.save(interaction));
    }

    @Override
    @Transactional
    public InteractionDto updateInteraction(Long id, InteractionDto interactionDto) {
        Interaction interaction = interactionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Interaction not found"));

        interaction.setInteractionType(interactionDto.getInteractionType());
        interaction.setSubject(interactionDto.getSubject());
        interaction.setNotes(interactionDto.getNotes());
        interaction.setInteractionDate(interactionDto.getInteractionDate());
        
        return mapToDto(interactionRepository.save(interaction));
    }

    @Override
    @Transactional
    public void deleteInteraction(Long id) {
        Interaction interaction = interactionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Interaction not found"));
        interactionRepository.delete(interaction);
    }

    private InteractionDto mapToDto(Interaction interaction) {
        return InteractionDto.builder()
                .interactionId(interaction.getInteractionId())
                .leadId(interaction.getLead().getLeadId())
                .userId(interaction.getUser().getUserId())
                .userName(interaction.getUser().getFullName())
                .interactionType(interaction.getInteractionType())
                .subject(interaction.getSubject())
                .notes(interaction.getNotes())
                .interactionDate(interaction.getInteractionDate())
                .build();
    }
}
