package com.crm.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * This is a Spring MVC Web Controller. It acts as the bridge between the user's browser and our application. It listens for HTTP requests (like clicking a link or submitting a form), talks to the Service layer to get data, and then returns an HTML view (Thymeleaf template) for the user to see.
 */
@Controller
public class AuthController {

    /**
     * Handles GET requests to the "/login" URL.
     * This is the endpoint Spring Security automatically redirects users to if they aren't authenticated.
     * 
     * @return The name of the Thymeleaf template to render (in this case, "auth/login.html").
     */
    @GetMapping("/login")
    public String loginPage() {
        // Tells Spring to look for a file at src/main/resources/templates/auth/login.html
        return "auth/login";
    }
}
