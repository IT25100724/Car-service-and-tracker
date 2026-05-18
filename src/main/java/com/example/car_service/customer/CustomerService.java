package com.example.car_service.customer;

import java.util.List;

// Abstraction
public interface CustomerService {
    
    Customer createCustomer(Customer customer);
    
    Customer registerCustomer(CustomerRegistrationDTO dto);
    Customer getCustomerById(Long id);
    Customer getCustomerByEmail(String email);
    List<Customer> getAllCustomers();
    Customer updateCustomer(Long id, Customer customer);
    Customer updateProfile(Long id, CustomerProfileDTO dto);
    void deleteCustomer(Long id);
}
