package com.crm.controller;

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

/**
 * This is a Spring MVC Web Controller. It acts as the bridge between the user's browser and our application. It listens for HTTP requests (like clicking a link or submitting a form), talks to the Service layer to get data, and then returns an HTML view (Thymeleaf template) for the user to see.
 */
@Controller
@RequestMapping("/customers")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;
    private final LeadRepository leadRepository;
    private final TaskRepository taskRepository;
    private final InteractionRepository interactionRepository;

    /**
     * Handles GET requests to "/customers".
     * Displays a list of all customers.
     * 
     * @param model Used to pass the list of customers to the HTML view
     * @return The "customers/list.html" template
     */
    @GetMapping
    public String listCustomers(@RequestParam(required = false) String search, Model model) {
        if (search != null && !search.trim().isEmpty()) {
            model.addAttribute("customers", customerService.searchCustomers(search));
            model.addAttribute("search", search);
        } else {
            model.addAttribute("customers", customerService.getAllCustomers());
        }
        model.addAttribute("pageTitle", "Customers");
        return "customers/list";
    }

    /**
     * Handles GET requests to "/customers/new".
     * Displays a blank form for the user to fill out a new customer's details.
     * 
     * @param model Used to pass an empty CustomerDto to bind to the form
     * @return The "customers/form.html" template
     */
    @GetMapping("/new")
    public String newCustomerForm(Model model) {
        model.addAttribute("customer", CustomerDto.builder().build());
        model.addAttribute("pageTitle", "Add Customer");
        return "customers/form";
    }

    /**
     * Handles POST requests to "/customers/new".
     * Saves the newly created customer form data to the database.
     * 
     * @param customer The form data bound to a CustomerDto object
     * @param result Captures any validation errors (e.g. invalid email format)
     * @param redirectAttributes Used to flash success/error messages
     * @return A redirect to the customer list, or back to the form if validation fails
     */
    @PostMapping("/new")
    public String createCustomer(@Valid @ModelAttribute("customer") CustomerDto customer,
                                 BindingResult result,
                                 RedirectAttributes redirectAttributes) {
        // If the user's input violates the validation rules, return them to the form to fix it
        if (result.hasErrors()) {
            return "customers/form";
        }
        customerService.createCustomer(customer);
        redirectAttributes.addFlashAttribute("successMessage", "Customer created successfully!");
        return "redirect:/customers";
    }

    /**
     * Handles GET requests to "/customers/{id}".
     * Shows a detailed view of a specific customer, including their leads, interactions, and tasks.
     * 
     * @param id The specific customer's ID extracted from the URL
     * @param model Used to pass the customer data to the view
     * @return The "customers/detail.html" template
     */
    @GetMapping("/{id}")
    public String customerDetail(@PathVariable Long id, Model model) {
        model.addAttribute("customer", customerService.getCustomerById(id));
        model.addAttribute("pageTitle", "Customer Details");
        // Fetch all related entities to show a comprehensive dashboard for this customer
        model.addAttribute("leads", leadRepository.findByCustomer_CustomerId(id));
        model.addAttribute("interactions", interactionRepository.findByLead_Customer_CustomerId(id));
        model.addAttribute("tasks", taskRepository.findByLead_Customer_CustomerId(id));
        return "customers/detail";
    }
    
    /**
     * Handles GET requests to "/customers/{id}/edit".
     * Displays a form pre-populated with an existing customer's data so they can be updated.
     * 
     * @param id The specific customer's ID to fetch and edit
     * @param model Used to pass the existing customer data to the form
     * @return The "customers/form.html" template
     */
    @GetMapping("/{id}/edit")
    public String editCustomerForm(@PathVariable Long id, Model model) {
        model.addAttribute("customer", customerService.getCustomerById(id));
        model.addAttribute("pageTitle", "Edit Customer");
        return "customers/form";
    }

    /**
     * Handles POST requests to "/customers/{id}/edit".
     * Saves the updated customer data from the edit form.
     * 
     * @param id The ID of the customer being updated
     * @param customer The updated data bound to a CustomerDto
     * @param result Captures validation errors
     * @param redirectAttributes Flashes success message
     * @return A redirect to the customer's detail page, or back to the form if validation fails
     */
    @PostMapping("/{id}/edit")
    public String updateCustomer(@PathVariable Long id,
                                 @Valid @ModelAttribute("customer") CustomerDto customer,
                                 BindingResult result,
                                 RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            return "customers/form";
        }
        customerService.updateCustomer(id, customer);
        redirectAttributes.addFlashAttribute("successMessage", "Customer updated successfully!");
        return "redirect:/customers/" + id;
    }

    /**
     * Handles POST requests to "/customers/{id}/delete".
     * Deletes a specific customer from the database.
     * 
     * @param id The ID of the customer to delete
     * @param redirectAttributes Flashes success message
     * @return A redirect to the main customers list
     */
    @PostMapping("/{id}/delete")
    public String deleteCustomer(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        customerService.deleteCustomer(id);
        redirectAttributes.addFlashAttribute("successMessage", "Customer deleted successfully!");
        return "redirect:/customers";
    }
}
