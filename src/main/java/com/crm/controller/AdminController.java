package com.crm.controller;

import com.crm.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.crm.entity.User;
import java.util.UUID;

/**
 * This is a Spring MVC Web Controller. It acts as the bridge between the user's browser and our application. It listens for HTTP requests (like clicking a link or submitting a form), talks to the Service layer to get data, and then returns an HTML view (Thymeleaf template) for the user to see.
 */
@Controller
@RequestMapping("/admin/users")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')") // This ensures that ONLY users with the ADMIN role can access these routes
public class AdminController {

    private final UserService userService;

    /**
     * Handles GET requests to "/admin/users".
     * Fetches all users from the database and displays them in a table.
     * 
     * @param model Used to pass data to the view
     * @return The "admin/users.html" template
     */
    @GetMapping
    public String listUsers(Model model) {
        model.addAttribute("users", userService.getAllUsers());
        model.addAttribute("pageTitle", "User Management");
        return "admin/users";
    }

    /**
     * Handles GET requests to "/admin/users/new".
     * Displays a blank form for creating a new user.
     * 
     * @param model Used to pass data to the view
     * @return The "admin/user-form.html" template
     */
    @GetMapping("/new")
    public String newUserForm(Model model) {
        // We pass an empty User object to bind the form fields to
        model.addAttribute("user", new User());
        
        // Pass all possible user roles (e.g. ADMIN, USER) to populate a dropdown in the HTML form
        model.addAttribute("roles", java.util.List.of("ADMIN", "USER"));
        
        model.addAttribute("pageTitle", "Create User");
        return "admin/user-form";
    }

    /**
     * Handles POST requests to "/admin/users/new".
     * Takes the data submitted from the HTML form and saves it to the database.
     * 
     * @param user The user object automatically populated by Spring from the form fields
     * @param redirectAttributes Used to pass messages (like success/error) after redirecting
     * @return A redirect command to send the browser back to the user list
     */
    @PostMapping("/new")
    public String createUser(@ModelAttribute("user") User user, RedirectAttributes redirectAttributes) {
        // Generate a random 8-character password for the new user
        String randomPassword = UUID.randomUUID().toString().substring(0, 8);
        user.setPassword(randomPassword);
        
        // Ensure new users are active by default
        user.setStatus("ACTIVE");
        
        // Save the user via the service layer
        userService.createUser(user);
        
        // Flash attributes survive a single redirect, allowing us to show a success message on the next page
        redirectAttributes.addFlashAttribute("successMessage", "User created successfully! Temporary password: " + randomPassword);
        
        // Redirecting prevents form re-submission if the user refreshes the page
        return "redirect:/admin/users";
    }

    /**
     * Handles POST requests to "/admin/users/{id}/deactivate".
     * Deactivates a specific user instead of deleting them.
     * 
     * @param id The ID of the user, extracted from the URL path
     * @param redirectAttributes Used to pass messages (like success/error) after redirecting
     * @return A redirect command to send the browser back to the user list
     */
    @PostMapping("/{id}/deactivate")
    public String deactivateUser(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        userService.deactivateUser(id);
        redirectAttributes.addFlashAttribute("successMessage", "User deactivated successfully!");
        return "redirect:/admin/users";
    }
}
