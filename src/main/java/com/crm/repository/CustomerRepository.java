package com.crm.repository;

import com.crm.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * This is a Repository interface. It allows us to perform database operations (Create, Read, Update, Delete) without writing complex SQL queries. Spring Data JPA generates the SQL for us automatically behind the scenes.
 */
@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    
    // Spring Data JPA will automatically generate the SQL for this based on the method name
    java.util.List<Customer> findByCustomerNameContainingIgnoreCaseOrCompanyNameContainingIgnoreCase(String name, String companyName);
}
