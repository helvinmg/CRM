package com.crm.web;

import com.crm.dto.CustomerDto;
import com.crm.repository.InteractionRepository;
import com.crm.repository.LeadRepository;
import com.crm.repository.TaskRepository;
import com.crm.service.CustomerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/customers")
@RequiredArgsConstructor
public class WebCustomerController {

    private final CustomerService customerService;
    private final LeadRepository leadRepository;
    private final TaskRepository taskRepository;
    private final InteractionRepository interactionRepository;

    @GetMapping
    public String listCustomers(Model model) {
        model.addAttribute("customers", customerService.getAllCustomers());
        model.addAttribute("pageTitle", "Customers");
        return "customers/list";
    }

    @GetMapping("/new")
    public String newCustomerForm(Model model) {
        model.addAttribute("customer", CustomerDto.builder().build());
        model.addAttribute("pageTitle", "Add Customer");
        return "customers/form";
    }

    @PostMapping("/new")
    public String createCustomer(@Valid @ModelAttribute("customer") CustomerDto customer,
                                 BindingResult result,
                                 RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "customers/form";
        }
        customerService.createCustomer(customer);
        redirectAttributes.addFlashAttribute("successMessage", "Customer created successfully!");
        return "redirect:/customers";
    }

    @GetMapping("/{id}")
    public String customerDetail(@PathVariable Long id, Model model) {
        model.addAttribute("customer", customerService.getCustomerById(id));
        model.addAttribute("pageTitle", "Customer Details");
        model.addAttribute("leads", leadRepository.findByCustomer_CustomerId(id));
        model.addAttribute("interactions", interactionRepository.findByLead_Customer_CustomerId(id));
        model.addAttribute("tasks", taskRepository.findByLead_Customer_CustomerId(id));
        return "customers/detail";
    }
}
