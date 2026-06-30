package com.crm.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

/**
 * This is a Spring MVC Web Controller. It acts as the bridge between the user's browser and our application. It listens for HTTP requests (like clicking a link or submitting a form), talks to the Service layer to get data, and then returns an HTML view (Thymeleaf template) for the user to see.
 */
@ControllerAdvice // This annotation makes the methods inside this class apply to ALL controllers globally
public class GlobalControllerAdvice {

    /**
     * The @ModelAttribute annotation here ensures that the "requestURI" variable 
     * is automatically added to the Model for EVERY single Thymeleaf template.
     * This is useful for UI logic, like highlighting the active link in a navigation bar.
     * 
     * @param request The current HTTP request
     * @return The URI (path) of the current request
     */
    @ModelAttribute("requestURI")
    public String requestURI(final HttpServletRequest request) {
        return request.getRequestURI();
    }
}
