package com.crm.web;

import com.crm.dto.TaskDto;
import com.crm.enums.TaskPriority;
import com.crm.service.LeadService;
import com.crm.service.TaskService;
import com.crm.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/tasks")
@RequiredArgsConstructor
public class WebTaskController {

    private final TaskService taskService;
    private final LeadService leadService;
    private final UserService userService;

    @GetMapping
    public String listTasks(Model model) {
        model.addAttribute("tasks", taskService.getAllTasks());
        model.addAttribute("pageTitle", "Tasks");
        return "tasks/list";
    }

    @GetMapping("/new")
    public String newTaskForm(Model model) {
        model.addAttribute("task", TaskDto.builder().build());
        model.addAttribute("leads", leadService.getAllLeads());
        model.addAttribute("users", userService.getAllUsers());
        model.addAttribute("priorities", TaskPriority.values());
        model.addAttribute("pageTitle", "Add Task");
        return "tasks/form";
    }

    @PostMapping("/new")
    public String createTask(@ModelAttribute("task") TaskDto taskDto, RedirectAttributes redirectAttributes) {
        taskService.createTask(taskDto);
        redirectAttributes.addFlashAttribute("successMessage", "Task created successfully!");
        return "redirect:/tasks";
    }

    @PostMapping("/{id}/complete")
    public String markComplete(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        taskService.markTaskComplete(id);
        redirectAttributes.addFlashAttribute("successMessage", "Task marked as complete!");
        return "redirect:/tasks";
    }
}
