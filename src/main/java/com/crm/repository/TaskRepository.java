package com.crm.repository;

import com.crm.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

/**
 * This is a Repository interface. It allows us to perform database operations (Create, Read, Update, Delete) without writing complex SQL queries. Spring Data JPA generates the SQL for us automatically behind the scenes.
 */
@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findByAssignedTo_UserId(Long userId);
    List<Task> findByDueDateBeforeAndStatusNot(LocalDate date, String status);
    List<Task> findByDueDateBetweenAndStatusNot(LocalDate start, LocalDate end, String status);
    List<Task> findByLead_Customer_CustomerId(Long customerId);
}
