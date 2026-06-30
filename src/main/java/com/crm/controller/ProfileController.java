package com.crm.controller;

import com.crm.entity.User;
import com.crm.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/profile")
@RequiredArgsConstructor
public class ProfileController {

    private final UserService userService;

    @GetMapping
    public String viewProfile(Authentication authentication, Model model) {
        String email = authentication.getName();
        User user = userService.findByEmail(email);
        model.addAttribute("user", user);
        model.addAttribute("pageTitle", "My Profile");
        return "profile/view";
    }

    @PostMapping
    public String updateProfile(@RequestParam String fullName, Authentication authentication, RedirectAttributes redirectAttributes) {
        String email = authentication.getName();
        userService.updateUserProfile(email, fullName);
        redirectAttributes.addFlashAttribute("successMessage", "Profile updated successfully!");
        return "redirect:/profile";
    }

    @GetMapping("/reset-password")
    public String resetPasswordForm(Model model) {
        model.addAttribute("pageTitle", "Reset Password");
        return "profile/reset-password";
    }

    @PostMapping("/reset-password")
    public String processResetPassword(@RequestParam String newPassword, @RequestParam String confirmPassword, 
                                       Authentication authentication, RedirectAttributes redirectAttributes) {
        if (!newPassword.equals(confirmPassword)) {
            redirectAttributes.addFlashAttribute("errorMessage", "Passwords do not match!");
            return "redirect:/profile/reset-password";
        }
        
        String email = authentication.getName();
        userService.changePassword(email, newPassword);
        redirectAttributes.addFlashAttribute("successMessage", "Password updated successfully!");
        return "redirect:/profile";
    }
}
