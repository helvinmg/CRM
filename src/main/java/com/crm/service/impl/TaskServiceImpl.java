package com.crm.service.impl;

import com.crm.dto.TaskDto;
import com.crm.entity.Lead;
import com.crm.entity.Task;
import com.crm.entity.User;
import com.crm.enums.TaskStatus;
import com.crm.exception.ResourceNotFoundException;
import com.crm.repository.LeadRepository;
import com.crm.repository.TaskRepository;
import com.crm.repository.UserRepository;
import com.crm.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final LeadRepository leadRepository;
    private final UserRepository userRepository;

    @Override
    public List<TaskDto> getAllTasks() {
        return taskRepository.findAll().stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<TaskDto> getTasksByUser(Long userId) {
        return taskRepository.findByAssignedTo_UserId(userId).stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public TaskDto getTaskById(Long id) {
        return taskRepository.findById(id)
                .map(this::mapToDto)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found"));
    }

    @Override
    @Transactional
    public TaskDto createTask(TaskDto taskDto) {
        Lead lead = leadRepository.findById(taskDto.getLeadId())
                .orElseThrow(() -> new ResourceNotFoundException("Lead not found"));
        User user = userRepository.findById(taskDto.getAssignedTo())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        Task task = Task.builder()
                .lead(lead)
                .assignedTo(user)
                .title(taskDto.getTitle())
                .description(taskDto.getDescription())
                .priority(taskDto.getPriority())
                .status(TaskStatus.PENDING)
                .dueDate(taskDto.getDueDate())
                .build();
        return mapToDto(taskRepository.save(task));
    }

    @Override
    @Transactional
    public TaskDto updateTask(Long id, TaskDto taskDto) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found"));

        if (taskDto.getAssignedTo() != null) {
            User user = userRepository.findById(taskDto.getAssignedTo())
                    .orElseThrow(() -> new ResourceNotFoundException("User not found"));
            task.setAssignedTo(user);
        }

        task.setTitle(taskDto.getTitle());
        task.setDescription(taskDto.getDescription());
        task.setPriority(taskDto.getPriority());
        task.setDueDate(taskDto.getDueDate());
        if (taskDto.getStatus() != null) {
            task.setStatus(taskDto.getStatus());
        }

        return mapToDto(taskRepository.save(task));
    }

    @Override
    @Transactional
    public void deleteTask(Long id) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found"));
        taskRepository.delete(task);
    }

    @Override
    @Transactional
    public TaskDto markTaskComplete(Long id) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found"));
        task.setStatus(TaskStatus.COMPLETED);
        return mapToDto(taskRepository.save(task));
    }

    private TaskDto mapToDto(Task task) {
        return TaskDto.builder()
                .taskId(task.getTaskId())
                .leadId(task.getLead().getLeadId())
                .assignedTo(task.getAssignedTo().getUserId())
                .assignedUserName(task.getAssignedTo().getFullName())
                .title(task.getTitle())
                .description(task.getDescription())
                .priority(task.getPriority())
                .status(task.getStatus())
                .dueDate(task.getDueDate())
                .build();
    }
}
