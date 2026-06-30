package com.crm.repository;

import com.crm.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * This is a Repository interface. It allows us to perform database operations (Create, Read, Update, Delete) without writing complex SQL queries. Spring Data JPA generates the SQL for us automatically behind the scenes.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
}
