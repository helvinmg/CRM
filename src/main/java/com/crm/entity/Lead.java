package com.crm.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * This class is a Database Entity. It tells Hibernate/JPA how to map Java objects directly into rows in the relational database table.
 */
@Entity
@Table(name = "leads")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Lead {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long leadId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "assigned_to")
    private User assignedTo;
    @Column(nullable = false)
    private String status;

    @Column(length = 150)
    private String title;

    @Column(length = 100)
    private String source;

    private LocalDate expectedCloseDate;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;
}
