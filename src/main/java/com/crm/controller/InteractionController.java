package com.crm.controller;

import com.crm.dto.InteractionDto;
import com.crm.service.InteractionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;

/**
 * This is a Spring MVC Web Controller. It acts as the bridge between the user's browser and our application. It listens for HTTP requests (like clicking a link or submitting a form), talks to the Service layer to get data, and then returns an HTML view (Thymeleaf template) for the user to see.
 */
@Controller
@RequestMapping("/interactions")
@RequiredArgsConstructor
public class InteractionController {

    private final InteractionService interactionService;

    /**
     * Handles GET requests to "/interactions".
     * Displays a list of all interactions logged across all leads.
     * 
     * @param model Used to pass the list of interactions to the view
     * @return The "interactions/list.html" template
     */
    @GetMapping
    public String listInteractions(Model model) {
        model.addAttribute("interactions", interactionService.getAllInteractions());
        model.addAttribute("pageTitle", "Interactions Log");
        return "interactions/list";
    }

    /**
     * Handles GET requests to "/interactions/new?leadId=X".
     * Shows a form to log a new interaction (like a phone call or email) for a specific lead.
     * 
     * @param leadId The ID of the lead this interaction is associated with (passed as a URL parameter)
     * @param model Used to pass data to the template
     * @return The "interactions/form.html" template
     */
    @GetMapping("/new")
    public String newInteractionForm(@RequestParam Long leadId, Model model) {
        // Pre-fill the interaction form with the current date and the associated lead ID
        InteractionDto interaction = InteractionDto.builder()
                .leadId(leadId)
                .interactionDate(LocalDate.now())
                .build();
        model.addAttribute("interaction", interaction);
        
        // Pass interaction types (CALL, EMAIL, MEETING) for the dropdown menu
        model.addAttribute("types", java.util.List.of("CALL", "EMAIL", "MEETING"));
        
        model.addAttribute("pageTitle", "Log Interaction");
        return "interactions/form";
    }

    /**
     * Handles POST requests to "/interactions/new".
     * Saves the submitted interaction log to the database.
     * 
     * @param interactionDto The interaction data submitted from the HTML form
     * @param redirectAttributes Used to flash a success message upon redirect
     * @return A redirect back to the associated lead's detail page
     */
    @PostMapping("/new")
    public String createInteraction(@ModelAttribute("interaction") InteractionDto interactionDto, RedirectAttributes redirectAttributes) {
        // Normally, we'd get the user ID from the Spring Security context
        // But for simplicity in this CRM, we are hardcoding it to user 1 for now
        interactionDto.setUserId(1L); 
        
        interactionService.createInteraction(interactionDto);
        redirectAttributes.addFlashAttribute("successMessage", "Interaction logged!");
        
        // Redirect back to the specific lead that this interaction belongs to
        return "redirect:/leads/" + interactionDto.getLeadId();
    }
}
