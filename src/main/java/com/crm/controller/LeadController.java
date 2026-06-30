package com.crm.controller;

import com.crm.dto.LeadDto;
import com.crm.repository.InteractionRepository;
import com.crm.service.CustomerService;
import com.crm.service.LeadService;
import com.crm.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * This is a Spring MVC Web Controller. It acts as the bridge between the user's browser and our application. It listens for HTTP requests (like clicking a link or submitting a form), talks to the Service layer to get data, and then returns an HTML view (Thymeleaf template) for the user to see.
 */
@Controller
@RequestMapping("/leads")
@RequiredArgsConstructor
public class LeadController {

    private final LeadService leadService;
    private final CustomerService customerService;
    private final UserService userService;
    private final InteractionRepository interactionRepository;
    private final com.crm.repository.LeadStatusHistoryRepository leadStatusHistoryRepository;

    /**
     * Handles GET requests to "/leads".
     * Displays all leads. Allows toggling between a table view and a kanban board view.
     * 
     * @param view A URL parameter (e.g., ?view=kanban). Defaults to "table" if not provided.
     * @param model Used to pass data to the template
     * @return The "leads/list.html" template
     */
    @GetMapping
    public String listLeads(@RequestParam(defaultValue = "table") String view, Model model) {
        model.addAttribute("leads", leadService.getAllLeads());
        model.addAttribute("viewMode", view); // "table" or "kanban"
        model.addAttribute("pageTitle", "Leads & Pipeline");
        model.addAttribute("statuses", java.util.List.of("NEW", "CONTACTED", "QUALIFIED", "PROPOSAL", "NEGOTIATION", "CLOSED_WON", "CLOSED_LOST"));
        return "leads/list";
    }

    /**
     * Handles GET requests to "/leads/new".
     * Shows a form for creating a new sales lead.
     * 
     * @param model Used to pass data to the template
     * @return The "leads/form.html" template
     */
    @GetMapping("/new")
    public String newLeadForm(Model model) {
        model.addAttribute("lead", LeadDto.builder().build());
        
        // Pass a list of customers and users so the frontend can populate dropdown selection menus
        model.addAttribute("customers", customerService.getAllCustomers());
        model.addAttribute("users", userService.getAllUsers());
        
        // Pass all possible lead statuses (e.g. NEW, CONTACTED, CLOSED_WON)
        model.addAttribute("statuses", java.util.List.of("NEW", "CONTACTED", "QUALIFIED", "PROPOSAL", "NEGOTIATION", "CLOSED_WON", "CLOSED_LOST"));
        
        model.addAttribute("pageTitle", "Add Lead");
        return "leads/form";
    }

    /**
     * Handles POST requests to "/leads/new".
     * Saves the submitted lead form to the database.
     * 
     * @param leadDto The submitted form data
     * @param redirectAttributes Used to flash a success message upon redirect
     * @return A redirect back to the leads list
     */
    @PostMapping("/new")
    public String createLead(@ModelAttribute("lead") LeadDto leadDto, RedirectAttributes redirectAttributes) {
        leadService.createLead(leadDto);
        redirectAttributes.addFlashAttribute("successMessage", "Lead created successfully!");
        return "redirect:/leads";
    }

    /**
     * Handles GET requests to "/leads/{id}".
     * Shows detailed info about a specific lead, including past interactions.
     * 
     * @param id The lead's ID
     * @param model Used to pass data to the template
     * @return The "leads/detail.html" template
     */
    @GetMapping("/{id}")
    public String leadDetail(@PathVariable Long id, Model model) {
        model.addAttribute("lead", leadService.getLeadById(id));
        model.addAttribute("interactions", interactionRepository.findByLead_LeadId(id));
        model.addAttribute("history", leadStatusHistoryRepository.findByLead_LeadIdOrderByChangeDateDesc(id));
        model.addAttribute("statuses", java.util.List.of("NEW", "CONTACTED", "QUALIFIED", "PROPOSAL", "NEGOTIATION", "CLOSED_WON", "CLOSED_LOST"));
        model.addAttribute("pageTitle", "Lead Pipeline");
        return "leads/detail";
    }

    /**
     * Handles POST requests to "/leads/{id}/status".
     * Allows a user to quickly update the status of a lead (e.g., from NEW to CONTACTED).
     * 
     * @param id The lead's ID
     * @param status The new status selected from a dropdown or button
     * @param redirectAttributes Used to flash a success message
     * @return A redirect back to the lead's detail page
     */
    @PostMapping("/{id}/status")
    public String updateStatus(@PathVariable Long id, @RequestParam String status, RedirectAttributes redirectAttributes) {
        leadService.updateLeadStatus(id, status);
        redirectAttributes.addFlashAttribute("successMessage", "Lead status updated to " + status + "!");
        return "redirect:/leads/" + id;
    }
}
