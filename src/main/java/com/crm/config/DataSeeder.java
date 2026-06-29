package com.crm.config;

import com.crm.entity.Customer;
import com.crm.entity.Interaction;
import com.crm.entity.Lead;
import com.crm.entity.Task;
import com.crm.entity.User;
import com.crm.enums.InteractionType;
import com.crm.enums.LeadStatus;
import com.crm.enums.TaskPriority;
import com.crm.enums.TaskStatus;
import com.crm.enums.UserRole;
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
            User admin = User.builder().fullName("Admin User").email("admin@crm.com").password(passwordEncoder.encode("admin123")).role(UserRole.ADMIN).status(com.crm.enums.UserStatus.ACTIVE).build();
            userRepository.save(admin);

            User salesUser = User.builder().fullName("Sales User").email("sales@crm.com").password(passwordEncoder.encode("user123")).role(UserRole.USER).status(com.crm.enums.UserStatus.ACTIVE).build();
            User salesUser2 = User.builder().fullName("John Doe").email("john@crm.com").password(passwordEncoder.encode("user123")).role(UserRole.USER).status(com.crm.enums.UserStatus.ACTIVE).build();
            userRepository.save(salesUser);
            userRepository.save(salesUser2);

            // Seed Customers
            Customer c1 = Customer.builder().customerName("Acme Corp").email("contact@acme.com").companyName("Acme").phone("555-0101").build();
            Customer c2 = Customer.builder().customerName("Stark Ind").email("tony@stark.com").companyName("Stark").phone("555-0102").build();
            Customer c3 = Customer.builder().customerName("Wayne Ent").email("bruce@wayne.com").companyName("Wayne").phone("555-0103").build();
            Customer c4 = Customer.builder().customerName("Oscorp").email("norman@oscorp.com").companyName("Oscorp").phone("555-0104").build();
            customerRepository.save(c1);
            customerRepository.save(c2);
            customerRepository.save(c3);
            customerRepository.save(c4);

            // Seed Leads
            Lead lead1 = Lead.builder().customer(c1).assignedTo(salesUser).status(LeadStatus.NEW).source("Website").expectedCloseDate(LocalDate.now().plusDays(10)).build();
            Lead lead2 = Lead.builder().customer(c2).assignedTo(salesUser).status(LeadStatus.CLOSED_WON).source("Referral").expectedCloseDate(LocalDate.now().minusDays(5)).build();
            Lead lead3 = Lead.builder().customer(c3).assignedTo(salesUser2).status(LeadStatus.PROPOSAL_SENT).source("Cold Call").expectedCloseDate(LocalDate.now().plusDays(2)).build();
            Lead lead4 = Lead.builder().customer(c4).assignedTo(salesUser2).status(LeadStatus.CONTACTED).source("Event").expectedCloseDate(LocalDate.now().plusDays(15)).build();
            Lead lead5 = Lead.builder().customer(c1).assignedTo(admin).status(LeadStatus.QUALIFIED).source("Website").expectedCloseDate(LocalDate.now().plusDays(7)).build();
            leadRepository.save(lead1);
            leadRepository.save(lead2);
            leadRepository.save(lead3);
            leadRepository.save(lead4);
            leadRepository.save(lead5);

            // Seed Tasks
            taskRepository.save(Task.builder().lead(lead1).assignedTo(salesUser).title("Follow up call").priority(TaskPriority.HIGH).status(TaskStatus.PENDING).dueDate(LocalDate.now().minusDays(1)).build());
            taskRepository.save(Task.builder().lead(lead2).assignedTo(salesUser).title("Send contract").priority(TaskPriority.MEDIUM).status(TaskStatus.COMPLETED).dueDate(LocalDate.now().minusDays(3)).build());
            taskRepository.save(Task.builder().lead(lead3).assignedTo(salesUser2).title("Prepare demo").priority(TaskPriority.HIGH).status(TaskStatus.IN_PROGRESS).dueDate(LocalDate.now().plusDays(1)).build());
            taskRepository.save(Task.builder().lead(lead4).assignedTo(salesUser2).title("Initial meeting").priority(TaskPriority.LOW).status(TaskStatus.PENDING).dueDate(LocalDate.now().plusDays(5)).build());
            taskRepository.save(Task.builder().lead(lead5).assignedTo(admin).title("Review requirements").priority(TaskPriority.MEDIUM).status(TaskStatus.PENDING).dueDate(LocalDate.now().plusDays(2)).build());

            // Seed Interactions
            interactionRepository.save(Interaction.builder().lead(lead1).user(salesUser).interactionType(InteractionType.CALL).subject("Discovery Call").notes("Discussed initial requirements and pricing.").interactionDate(LocalDate.now().minusDays(2)).build());
            interactionRepository.save(Interaction.builder().lead(lead2).user(salesUser).interactionType(InteractionType.EMAIL).subject("Contract Sent").notes("Sent final contract for signature.").interactionDate(LocalDate.now().minusDays(4)).build());
            interactionRepository.save(Interaction.builder().lead(lead3).user(salesUser2).interactionType(InteractionType.MEETING).subject("Product Demo").notes("Showcased new features. Customer was very impressed.").interactionDate(LocalDate.now().minusDays(1)).build());
            interactionRepository.save(Interaction.builder().lead(lead4).user(salesUser2).interactionType(InteractionType.CALL).subject("Cold Call").notes("Left voicemail.").interactionDate(LocalDate.now().minusDays(3)).build());
            interactionRepository.save(Interaction.builder().lead(lead1).user(salesUser).interactionType(InteractionType.EMAIL).subject("Follow Up").notes("Checking in on discovery call.").interactionDate(LocalDate.now().minusDays(1)).build());
            
            System.out.println("Data seeding complete!");
        }
    }
}
