package com.crm.web;

import com.crm.dto.InteractionDto;
import com.crm.enums.InteractionType;
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

@Controller
@RequestMapping("/interactions")
@RequiredArgsConstructor
public class WebInteractionController {

    private final InteractionService interactionService;

    @GetMapping
    public String listInteractions(Model model) {
        model.addAttribute("interactions", interactionService.getAllInteractions());
        model.addAttribute("pageTitle", "Interactions Log");
        return "interactions/list";
    }

    @GetMapping("/new")
    public String newInteractionForm(@RequestParam Long leadId, Model model) {
        InteractionDto interaction = InteractionDto.builder()
                .leadId(leadId)
                .interactionDate(LocalDate.now())
                .build();
        model.addAttribute("interaction", interaction);
        model.addAttribute("types", InteractionType.values());
        model.addAttribute("pageTitle", "Log Interaction");
        return "interactions/form";
    }

    @PostMapping("/new")
    public String createInteraction(@ModelAttribute("interaction") InteractionDto interactionDto, RedirectAttributes redirectAttributes) {
        // Normally, get user ID from security context
        interactionDto.setUserId(1L); // Hardcoded admin for now
        interactionService.createInteraction(interactionDto);
        redirectAttributes.addFlashAttribute("successMessage", "Interaction logged!");
        return "redirect:/leads/" + interactionDto.getLeadId();
    }
}
