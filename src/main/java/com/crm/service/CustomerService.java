package com.crm.service;

import com.crm.dto.CustomerDto;
import java.util.List;

/**
 * This is a Service layer class. It contains the core 'business logic' of our application. Controllers call services to fetch or modify data, and services interact with repositories to save those changes.
 */
public interface CustomerService {
    List<CustomerDto> getAllCustomers();
    List<CustomerDto> searchCustomers(String keyword);
    CustomerDto getCustomerById(Long id);
    CustomerDto createCustomer(CustomerDto customerDto);
    CustomerDto updateCustomer(Long id, CustomerDto customerDto);
    void deleteCustomer(Long id);
}
