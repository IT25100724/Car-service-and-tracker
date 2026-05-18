package com.example.car_service.customer;

import com.example.car_service.exception.DuplicateResourceException;
import com.example.car_service.exception.ResourceNotFoundException;
import com.example.car_service.user.User;
import com.example.car_service.user.UserRepository;
import com.example.car_service.user.UserRole;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
// Polymorphism
public class CustomerServiceImpl implements CustomerService {

    // Encapsulation
    private final CustomerRepository customerRepository;
    private final UserRepository userRepository;

    // Polymorphism
    @Override
    @Transactional
    public Customer createCustomer(Customer customer) {
        if (customerRepository.existsByEmail(customer.getEmail())) {
            throw new DuplicateResourceException("Customer with email '" + customer.getEmail() + "' already exists");
        }
        return customerRepository.save(customer);
    }

    @Override
    @Transactional
    public Customer registerCustomer(CustomerRegistrationDTO dto) {
        if (customerRepository.existsByEmail(dto.getEmail())) {
            throw new DuplicateResourceException("Email already registered: " + dto.getEmail());
        }
        if (dto.getUsername() != null && userRepository.existsByUsername(dto.getUsername())) {
            throw new DuplicateResourceException("Username already taken: " + dto.getUsername());
        }

        Customer customer = Customer.builder()
                .firstName(dto.getFirstName())
                .lastName(dto.getLastName())
                .email(dto.getEmail())
                .phone(dto.getPhone())
                .address(dto.getAddress())
                .build();
        customer = customerRepository.save(customer);

        String username = (dto.getUsername() != null && !dto.getUsername().isBlank())
                ? dto.getUsername()
                : dto.getEmail().split("@")[0];

        String rawPassword = (dto.getPassword() != null && !dto.getPassword().isBlank())
                ? dto.getPassword() : "changeme";

        User user = User.builder()
                .username(username)
                .email(dto.getEmail())
                .passwordHash(rawPassword)
                .role(UserRole.CUSTOMER)
                .customerId(customer.getId())
                .isActive(true)
                .build();
        userRepository.save(user);

        return customer;
    }

    @Override
    public Customer getCustomerById(Long id) {
        return customerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found with id: " + id));
    }

    @Override
    public Customer getCustomerByEmail(String email) {
        return customerRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found with email: " + email));
    }

    @Override
    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    @Override
    @Transactional
    public Customer updateCustomer(Long id, Customer customer) {
        Customer existing = getCustomerById(id);
        
        if (!existing.getEmail().equals(customer.getEmail()) && customerRepository.existsByEmail(customer.getEmail())) {
            throw new DuplicateResourceException("Email already in use: " + customer.getEmail());
        }

        existing.setFirstName(customer.getFirstName());
        existing.setLastName(customer.getLastName());
        existing.setEmail(customer.getEmail());
        existing.setPhone(customer.getPhone());
        existing.setAddress(customer.getAddress());

        userRepository.findByCustomerId(id).ifPresent(user -> {
            user.setEmail(customer.getEmail());
            userRepository.save(user);
        });

        return customerRepository.save(existing);
    }

    @Override
    @Transactional
    public void deleteCustomer(Long id) {
        getCustomerById(id);
        
        userRepository.findByCustomerId(id).ifPresent(user -> userRepository.delete(user));
        
        customerRepository.deleteById(id);
    }

    @Override
    @Transactional
    public Customer updateProfile(Long id, CustomerProfileDTO dto) {
        Customer customer = getCustomerById(id);
        if (dto.getFirstName() != null) customer.setFirstName(dto.getFirstName());
        if (dto.getLastName() != null) customer.setLastName(dto.getLastName());
        if (dto.getPhone() != null) customer.setPhone(dto.getPhone());
        if (dto.getAddress() != null) customer.setAddress(dto.getAddress());

        if (dto.getEmail() != null && !dto.getEmail().equals(customer.getEmail())) {
            if (customerRepository.existsByEmail(dto.getEmail())) {
                throw new DuplicateResourceException("Email already in use: " + dto.getEmail());
            }
            customer.setEmail(dto.getEmail());
        }

        customer = customerRepository.save(customer);

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

        if (dto.getEmail() != null && !dto.getEmail().isBlank()) {
            userRepository.findByCustomerId(id).ifPresent(user -> {
                user.setEmail(dto.getEmail());
                userRepository.save(user);
            });
        }

        return customer;
    }
}