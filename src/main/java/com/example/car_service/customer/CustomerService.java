package com.example.car_service.customer;

import com.example.car_service.customer.CustomerController.CustomerProfileDTO;
import com.example.car_service.customer.CustomerController.CustomerRegistrationDTO;
import com.example.car_service.exception.DuplicateResourceException;
import com.example.car_service.exception.ResourceNotFoundException;
import com.example.car_service.user.User;
import com.example.car_service.user.UserRepository;
import com.example.car_service.user.User.UserRole;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

// Service class for managing customer all logic and CRUD operations
@Service
public class CustomerService {

    // Encapsulation (Attribute as Private)
    private final CustomerRepository customerRepository;
    private final UserRepository userRepository;


    // Constructor
    public CustomerService(CustomerRepository customerRepository, UserRepository userRepository) {
        this.customerRepository = customerRepository;
        this.userRepository = userRepository;
    }

    // Create a customer profile, checking for duplicate email first
    @Transactional // rolled back auto
    public Customer createCustomer(Customer customer) {
        if (customerRepository.existsByEmail(customer.getEmail())) {
            throw new DuplicateResourceException("Customer with email '" + customer.getEmail() + "' already exists");
        }
        return customerRepository.save(customer);
    }

    // Register a customer and dynamically create their linked user account
    @Transactional
    public Customer registerCustomer(CustomerRegistrationDTO dto) {
        if (customerRepository.existsByEmail(dto.getEmail())) {
            throw new DuplicateResourceException("Email already registered: " + dto.getEmail());
        }
        if (dto.getUsername() != null && userRepository.existsByUsername(dto.getUsername())) {
            throw new DuplicateResourceException("Username already taken: " + dto.getUsername());
        }

        // Create customer object
        Customer customer = new Customer(
                dto.getFirstName(),
                dto.getLastName(),
                dto.getEmail(),
                dto.getPhone(),
                dto.getAddress()
        );
        customer = customerRepository.save(customer);

        String username = (dto.getUsername() != null && !dto.getUsername().isBlank())
                ? dto.getUsername()
                : dto.getEmail().split("@")[0];

        String rawPassword = (dto.getPassword() != null && !dto.getPassword().isBlank())
                ? dto.getPassword() : "changeme";


        // Create and save user account
        User user = new User(
                username,
                dto.getEmail(),
                rawPassword,
                UserRole.CUSTOMER,
                customer.getId(),
                true
        );
        userRepository.save(user);

        return customer;
    }

    // Find a customer by ID or not >  exception if not found
    public Customer getCustomerById(Long id) {
        return customerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found with id: " + id));
    }

    // Find a customer by email or not > exception if not found
    public Customer getCustomerByEmail(String email) {
        return customerRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found with email: " + email));
    }

    // Retrieve all customer profiles
    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    // Update general customer profile fields, synchronizing the email with their user account
    @Transactional
    public Customer updateCustomer(Long id, Customer customer) {
        Customer existing = getCustomerById(id);

        // Check email duplication
        if (!existing.getEmail().equals(customer.getEmail()) && customerRepository.existsByEmail(customer.getEmail())) {
            throw new DuplicateResourceException("Email already in use: " + customer.getEmail());
        }

        // update existing to new data
        existing.setFirstName(customer.getFirstName());
        existing.setLastName(customer.getLastName());
        existing.setEmail(customer.getEmail());
        existing.setPhone(customer.getPhone());
        existing.setAddress(customer.getAddress());


        // Keep both tables in sync
        userRepository.findByCustomerId(id).ifPresent(user -> {
            user.setEmail(customer.getEmail());
            userRepository.save(user);
        });

        return customerRepository.save(existing);
    }

    // Delete a customer profile and their associated user account
    @Transactional
    public void deleteCustomer(Long id) {
        getCustomerById(id);
        userRepository.findByCustomerId(id).ifPresent(userRepository::delete);
        customerRepository.deleteById(id);
    }

    // Update customer profile DTO fields and change password if verified
    @Transactional
    public Customer updateProfile(Long id, CustomerProfileDTO dto) {
        Customer customer = getCustomerById(id);
        if (dto.getFirstName() != null) customer.setFirstName(dto.getFirstName());
        if (dto.getLastName() != null) customer.setLastName(dto.getLastName());
        if (dto.getPhone() != null) customer.setPhone(dto.getPhone());
        if (dto.getAddress() != null) customer.setAddress(dto.getAddress());


        // Check update email null or not
        if (dto.getEmail() != null && !dto.getEmail().equals(customer.getEmail())) {
            if (customerRepository.existsByEmail(dto.getEmail())) {
                throw new DuplicateResourceException("Email already in use: " + dto.getEmail());
            }
            customer.setEmail(dto.getEmail());
        }

        customer = customerRepository.save(customer);


        // Update new password
        if (dto.getNewPassword() != null && !dto.getNewPassword().isBlank()) {
            userRepository.findByEmail(customer.getEmail()).ifPresent(user -> {
                if (dto.getCurrentPassword() == null || dto.getCurrentPassword().isBlank()) {
                    throw new IllegalArgumentException("Current password is required to change password");
                }
                if (!dto.getCurrentPassword().equals(user.getPasswordHash())) {
                    throw new IllegalArgumentException("Current password is incorrect");
                }
                user.setPasswordHash(dto.getNewPassword());
                userRepository.save(user);
            });
        }


        // If change the email and also update linked user email
        if (dto.getEmail() != null && !dto.getEmail().isBlank()) {
            userRepository.findByCustomerId(id).ifPresent(user -> {
                user.setEmail(dto.getEmail());
                userRepository.save(user);
            });
        }

        return customer;
    }
}
