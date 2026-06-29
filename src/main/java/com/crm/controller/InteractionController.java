package com.crm.controller;

import com.crm.dto.InteractionDto;
import com.crm.service.InteractionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/interactions")
@RequiredArgsConstructor
public class InteractionController {

    private final InteractionService interactionService;

    @GetMapping
    public ResponseEntity<List<InteractionDto>> getInteractionsByLead(@RequestParam Long leadId) {
        return ResponseEntity.ok(interactionService.getInteractionsByLead(leadId));
    }

    @PostMapping
    public ResponseEntity<InteractionDto> createInteraction(@RequestBody InteractionDto interactionDto) {
        return new ResponseEntity<>(interactionService.createInteraction(interactionDto), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<InteractionDto> updateInteraction(@PathVariable Long id, @RequestBody InteractionDto interactionDto) {
        return ResponseEntity.ok(interactionService.updateInteraction(id, interactionDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteInteraction(@PathVariable Long id) {
        interactionService.deleteInteraction(id);
        return ResponseEntity.noContent().build();
    }
}
