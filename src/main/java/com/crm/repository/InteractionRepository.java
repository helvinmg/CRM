package com.crm.repository;

import com.crm.entity.Interaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * This is a Repository interface. It allows us to perform database operations (Create, Read, Update, Delete) without writing complex SQL queries. Spring Data JPA generates the SQL for us automatically behind the scenes.
 */
@Repository
public interface InteractionRepository extends JpaRepository<Interaction, Long> {
    List<Interaction> findByLead_LeadId(Long leadId);
    List<Interaction> findByLead_Customer_CustomerId(Long customerId);
}
