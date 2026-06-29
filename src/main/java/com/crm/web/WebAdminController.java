package com.crm.web;

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
import com.crm.enums.UserRole;
import com.crm.enums.UserStatus;
import java.util.UUID;

@Controller
@RequestMapping("/admin/users")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class WebAdminController {

    private final UserService userService;

    @GetMapping
    public String listUsers(Model model) {
        model.addAttribute("users", userService.getAllUsers());
        model.addAttribute("pageTitle", "User Management");
        return "admin/users";
    }

    @GetMapping("/new")
    public String newUserForm(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("roles", UserRole.values());
        model.addAttribute("pageTitle", "Create User");
        return "admin/user-form";
    }

    @PostMapping("/new")
    public String createUser(@ModelAttribute("user") User user, RedirectAttributes redirectAttributes) {
        String randomPassword = UUID.randomUUID().toString().substring(0, 8);
        user.setPassword(randomPassword);
        user.setStatus(UserStatus.ACTIVE);
        userService.createUser(user);
        redirectAttributes.addFlashAttribute("successMessage", "User created successfully! Temporary password: " + randomPassword);
        return "redirect:/admin/users";
    }

    @PostMapping("/{id}/deactivate")
    public String deactivateUser(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        userService.deactivateUser(id);
        redirectAttributes.addFlashAttribute("successMessage", "User deactivated successfully!");
        return "redirect:/admin/users";
    }
}
