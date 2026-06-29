package com.crm.repository;

import com.crm.entity.Task;
import com.crm.enums.TaskStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findByAssignedTo_UserId(Long userId);
    List<Task> findByDueDateBeforeAndStatusNot(LocalDate date, TaskStatus status);
    List<Task> findByDueDateBetweenAndStatusNot(LocalDate start, LocalDate end, TaskStatus status);
    List<Task> findByLead_Customer_CustomerId(Long customerId);
}
