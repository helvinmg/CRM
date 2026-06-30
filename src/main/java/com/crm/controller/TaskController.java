package com.crm.controller;

import com.crm.dto.TaskDto;
import com.crm.service.LeadService;
import com.crm.service.TaskService;
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
@RequestMapping("/tasks")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;
    private final LeadService leadService;
    private final UserService userService;

    /**
     * Handles GET requests to "/tasks".
     * Displays a list of all tasks.
     * 
     * @param model Used to pass the list of tasks to the view
     * @return The "tasks/list.html" template
     */
    @GetMapping
    public String listTasks(Model model) {
        model.addAttribute("tasks", taskService.getAllTasks());
        model.addAttribute("pageTitle", "Tasks");
        return "tasks/list";
    }

    /**
     * Handles GET requests to "/tasks/new".
     * Shows a form for creating a new task.
     * 
     * @param model Used to pass necessary options (leads, users, priorities) for dropdown menus
     * @return The "tasks/form.html" template
     */
    @GetMapping("/new")
    public String newTaskForm(Model model) {
        model.addAttribute("task", TaskDto.builder().build());
        model.addAttribute("leads", leadService.getAllLeads());
        model.addAttribute("users", userService.getAllUsers());
        model.addAttribute("priorities", java.util.List.of("LOW", "MEDIUM", "HIGH"));
        model.addAttribute("pageTitle", "Add Task");
        return "tasks/form";
    }

    /**
     * Handles POST requests to "/tasks/new".
     * Saves the newly created task to the database.
     * 
     * @param taskDto The data submitted from the HTML form
     * @param redirectAttributes Flashes a success message upon redirect
     * @return A redirect to the tasks list
     */
    @PostMapping("/new")
    public String createTask(@ModelAttribute("task") TaskDto taskDto, RedirectAttributes redirectAttributes) {
        taskService.createTask(taskDto);
        redirectAttributes.addFlashAttribute("successMessage", "Task created successfully!");
        return "redirect:/tasks";
    }

    /**
     * Handles POST requests to "/tasks/{id}/complete".
     * A quick action to mark a specific task as COMPLETED.
     * 
     * @param id The ID of the task to complete
     * @param redirectAttributes Flashes a success message
     * @return A redirect back to the tasks list
     */
    @PostMapping("/{id}/complete")
    public String markComplete(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        taskService.markTaskComplete(id);
        redirectAttributes.addFlashAttribute("successMessage", "Task marked as complete!");
        return "redirect:/tasks";
    }

    /**
     * Handles POST requests to "/tasks/{id}/status".
     * Updates a task to any valid status.
     * 
     * @param id The ID of the task to update
     * @param status The new status
     * @return A redirect back to the tasks list
     */
    @PostMapping("/{id}/status")
    public String updateStatus(@PathVariable Long id, @RequestParam String status, RedirectAttributes redirectAttributes) {
        taskService.updateTaskStatus(id, status);
        redirectAttributes.addFlashAttribute("successMessage", "Task status updated!");
        return "redirect:/tasks";
    }
}
