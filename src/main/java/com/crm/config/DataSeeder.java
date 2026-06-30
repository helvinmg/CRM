package com.crm.config;

import com.crm.entity.Customer;
import com.crm.entity.Interaction;
import com.crm.entity.Lead;
import com.crm.entity.Task;
import com.crm.entity.User;
import com.crm.repository.CustomerRepository;
import com.crm.repository.InteractionRepository;
import com.crm.repository.LeadRepository;
import com.crm.repository.TaskRepository;
import com.crm.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
@RequiredArgsConstructor
public class DataSeeder implements CommandLineRunner {

    private final UserRepository userRepository;
    private final CustomerRepository customerRepository;
    private final LeadRepository leadRepository;
    private final TaskRepository taskRepository;
    private final InteractionRepository interactionRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        if (userRepository.count() == 0) {
            // Seed Users
            User admin = User.builder().fullName("Admin User").email("admin@crm.com").password(passwordEncoder.encode("admin123")).role("ADMIN").status("ACTIVE").build();
            User salesUser = User.builder().fullName("Sales User").email("sales@crm.com").password(passwordEncoder.encode("user123")).role("USER").status("ACTIVE").build();
            User salesUser2 = User.builder().fullName("John Doe").email("john@crm.com").password(passwordEncoder.encode("user123")).role("USER").status("ACTIVE").build();
            userRepository.save(admin);
            userRepository.save(salesUser);
            userRepository.save(salesUser2);

            // Seed Customers
            Customer c1 = Customer.builder().customerName("Acme Corp").email("contact@acme.com").companyName("Acme").phone("555-0101").build();
            Customer c2 = Customer.builder().customerName("Stark Ind").email("tony@stark.com").companyName("Stark").phone("555-0102").build();
            Customer c3 = Customer.builder().customerName("Wayne Ent").email("bruce@wayne.com").companyName("Wayne").phone("555-0103").build();
            Customer c4 = Customer.builder().customerName("Oscorp").email("norman@oscorp.com").companyName("Oscorp").phone("555-0104").build();
            Customer c5 = Customer.builder().customerName("LexCorp").email("lex@lexcorp.com").companyName("LexCorp").phone("555-0105").build();
            Customer c6 = Customer.builder().customerName("Daily Planet").email("clark@dailyplanet.com").companyName("Daily Planet").phone("555-0106").build();
            customerRepository.save(c1);
            customerRepository.save(c2);
            customerRepository.save(c3);
            customerRepository.save(c4);
            customerRepository.save(c5);
            customerRepository.save(c6);

            // Seed Leads (covering ALL statuses: NEW, CONTACTED, QUALIFIED, PROPOSAL, NEGOTIATION, CLOSED_WON, CLOSED_LOST)
            Lead lead1 = Lead.builder().customer(c1).title("Q3 Software License").assignedTo(salesUser).status("NEW").source("Website").expectedCloseDate(LocalDate.now().plusDays(10)).build();
            Lead lead2 = Lead.builder().customer(c2).title("Server Upgrade 2026").assignedTo(salesUser).status("CLOSED_WON").source("Referral").expectedCloseDate(LocalDate.now().minusDays(5)).build();
            Lead lead3 = Lead.builder().customer(c3).title("Consulting Engagement").assignedTo(salesUser2).status("PROPOSAL").source("Cold Call").expectedCloseDate(LocalDate.now().plusDays(2)).build();
            Lead lead4 = Lead.builder().customer(c4).title("Annual Maintenance").assignedTo(salesUser2).status("CONTACTED").source("Event").expectedCloseDate(LocalDate.now().plusDays(15)).build();
            Lead lead5 = Lead.builder().customer(c1).title("Cloud Migration").assignedTo(admin).status("QUALIFIED").source("Website").expectedCloseDate(LocalDate.now().plusDays(7)).build();
            Lead lead6 = Lead.builder().customer(c5).title("Enterprise Support").assignedTo(salesUser).status("NEGOTIATION").source("Direct").expectedCloseDate(LocalDate.now().plusDays(4)).build();
            Lead lead7 = Lead.builder().customer(c6).title("Media Partnership").assignedTo(salesUser2).status("CLOSED_LOST").source("Social Media").expectedCloseDate(LocalDate.now().minusDays(10)).build();
            Lead lead8 = Lead.builder().customer(c3).title("New API Integration").assignedTo(salesUser).status("NEW").source("Referral").expectedCloseDate(LocalDate.now().plusDays(12)).build();
            Lead lead9 = Lead.builder().customer(c5).title("Security Audit").assignedTo(salesUser2).status("QUALIFIED").source("Event").expectedCloseDate(LocalDate.now().plusDays(8)).build();
            Lead lead10 = Lead.builder().customer(c2).title("Hardware Refresh").assignedTo(admin).status("NEGOTIATION").source("Cold Call").expectedCloseDate(LocalDate.now().plusDays(1)).build();
            
            leadRepository.save(lead1);
            leadRepository.save(lead2);
            leadRepository.save(lead3);
            leadRepository.save(lead4);
            leadRepository.save(lead5);
            leadRepository.save(lead6);
            leadRepository.save(lead7);
            leadRepository.save(lead8);
            leadRepository.save(lead9);
            leadRepository.save(lead10);

            // Seed Tasks
            taskRepository.save(Task.builder().lead(lead1).assignedTo(salesUser).title("Follow up call").priority("HIGH").status("PENDING").dueDate(LocalDate.now().minusDays(1)).build()); // OVERDUE
            taskRepository.save(Task.builder().lead(lead2).assignedTo(salesUser).title("Send contract").priority("MEDIUM").status("COMPLETED").dueDate(LocalDate.now().minusDays(3)).build());
            taskRepository.save(Task.builder().lead(lead3).assignedTo(salesUser2).title("Prepare demo").priority("HIGH").status("IN_PROGRESS").dueDate(LocalDate.now().plusDays(1)).build());
            taskRepository.save(Task.builder().lead(lead4).assignedTo(salesUser2).title("Initial meeting").priority("LOW").status("PENDING").dueDate(LocalDate.now().plusDays(5)).build());
            taskRepository.save(Task.builder().lead(lead5).assignedTo(admin).title("Review requirements").priority("MEDIUM").status("PENDING").dueDate(LocalDate.now().plusDays(2)).build());
            taskRepository.save(Task.builder().lead(lead6).assignedTo(salesUser).title("Send updated pricing").priority("HIGH").status("PENDING").dueDate(LocalDate.now().minusDays(2)).build()); // OVERDUE
            taskRepository.save(Task.builder().lead(lead7).assignedTo(salesUser2).title("Exit interview").priority("LOW").status("COMPLETED").dueDate(LocalDate.now().minusDays(8)).build());
            taskRepository.save(Task.builder().lead(lead8).assignedTo(salesUser).title("Initial outreach").priority("MEDIUM").status("PENDING").dueDate(LocalDate.now().plusDays(3)).build());
            taskRepository.save(Task.builder().lead(lead9).assignedTo(salesUser2).title("Check references").priority("LOW").status("IN_PROGRESS").dueDate(LocalDate.now().plusDays(4)).build());
            taskRepository.save(Task.builder().lead(lead10).assignedTo(admin).title("Finalize terms").priority("HIGH").status("PENDING").dueDate(LocalDate.now().plusDays(1)).build());

            // Seed Interactions
            interactionRepository.save(Interaction.builder().lead(lead1).user(salesUser).interactionType("CALL").subject("Discovery Call").notes("Discussed initial requirements and pricing.").interactionDate(LocalDate.now().minusDays(2)).build());
            interactionRepository.save(Interaction.builder().lead(lead2).user(salesUser).interactionType("EMAIL").subject("Contract Sent").notes("Sent final contract for signature.").interactionDate(LocalDate.now().minusDays(4)).build());
            interactionRepository.save(Interaction.builder().lead(lead3).user(salesUser2).interactionType("MEETING").subject("Product Demo").notes("Showcased new features. Customer was very impressed.").interactionDate(LocalDate.now().minusDays(1)).build());
            interactionRepository.save(Interaction.builder().lead(lead4).user(salesUser2).interactionType("CALL").subject("Cold Call").notes("Left voicemail.").interactionDate(LocalDate.now().minusDays(3)).build());
            interactionRepository.save(Interaction.builder().lead(lead1).user(salesUser).interactionType("EMAIL").subject("Follow Up").notes("Checking in on discovery call.").interactionDate(LocalDate.now().minusDays(1)).build());
            interactionRepository.save(Interaction.builder().lead(lead6).user(salesUser).interactionType("MEETING").subject("Negotiation meeting").notes("Customer wants a 10% discount.").interactionDate(LocalDate.now().minusDays(1)).build());
            interactionRepository.save(Interaction.builder().lead(lead7).user(salesUser2).interactionType("EMAIL").subject("Lost deal").notes("Customer went with a competitor.").interactionDate(LocalDate.now().minusDays(10)).build());
            interactionRepository.save(Interaction.builder().lead(lead9).user(salesUser2).interactionType("CALL").subject("Qualification").notes("Customer fits our ideal profile.").interactionDate(LocalDate.now().minusDays(5)).build());
            interactionRepository.save(Interaction.builder().lead(lead10).user(admin).interactionType("MEETING").subject("Terms discussion").notes("Agreed on final terms, preparing documents.").interactionDate(LocalDate.now().minusDays(2)).build());
            
            System.out.println("Data seeding complete!");
        }
    }
}
