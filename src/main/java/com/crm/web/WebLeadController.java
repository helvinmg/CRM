package com.crm.web;

import com.crm.dto.LeadDto;
import com.crm.enums.LeadStatus;
import com.crm.repository.InteractionRepository;
import com.crm.service.CustomerService;
import com.crm.service.LeadService;
import com.crm.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/leads")
@RequiredArgsConstructor
public class WebLeadController {

    private final LeadService leadService;
    private final CustomerService customerService;
    private final UserService userService;
    private final InteractionRepository interactionRepository;

    @GetMapping
    public String listLeads(@RequestParam(defaultValue = "table") String view, Model model) {
        model.addAttribute("leads", leadService.getAllLeads());
        model.addAttribute("viewMode", view); // "table" or "kanban"
        model.addAttribute("pageTitle", "Leads & Pipeline");
        return "leads/list";
    }

    @GetMapping("/new")
    public String newLeadForm(Model model) {
        model.addAttribute("lead", LeadDto.builder().build());
        model.addAttribute("customers", customerService.getAllCustomers());
        model.addAttribute("users", userService.getAllUsers());
        model.addAttribute("statuses", LeadStatus.values());
        model.addAttribute("pageTitle", "Add Lead");
        return "leads/form";
    }

    @PostMapping("/new")
    public String createLead(@ModelAttribute("lead") LeadDto leadDto, RedirectAttributes redirectAttributes) {
        leadService.createLead(leadDto);
        redirectAttributes.addFlashAttribute("successMessage", "Lead created successfully!");
        return "redirect:/leads";
    }

    @GetMapping("/{id}")
    public String leadDetail(@PathVariable Long id, Model model) {
        model.addAttribute("lead", leadService.getLeadById(id));
        model.addAttribute("interactions", interactionRepository.findByLead_LeadId(id));
        model.addAttribute("statuses", LeadStatus.values());
        model.addAttribute("pageTitle", "Lead Pipeline");
        return "leads/detail";
    }

    @PostMapping("/{id}/status")
    public String updateStatus(@PathVariable Long id, @RequestParam LeadStatus status, RedirectAttributes redirectAttributes) {
        leadService.updateLeadStatus(id, status);
        redirectAttributes.addFlashAttribute("successMessage", "Lead status updated to " + status + "!");
        return "redirect:/leads/" + id;
    }
}
