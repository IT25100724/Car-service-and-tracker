package com.example.car_service.customer;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customers")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class CustomerController {

    private final CustomerService customerService;

    // ENCAPSULATION: Receives a Customer object from the frontend and passes it to the service layer.
    @PostMapping
    public Customer createCustomer(@RequestBody Customer customer) {
        return customerService.createCustomer(customer);
    }

    // ENCAPSULATION & ABSTRACTION: Takes a bundled registration DTO and creates both Customer and User accounts.
    @PostMapping("/register")
    public Customer registerCustomer(@RequestBody CustomerRegistrationDTO registrationDTO) {
        return customerService.registerCustomer(registrationDTO);
    }

    // ABSTRACTION: Handles GET requests to fetch a customer profile by their ID.
    @GetMapping("/{id}")
    public Customer getCustomerById(@PathVariable Long id) {
        return customerService.getCustomerById(id);
    }

    // ABSTRACTION: Handles GET requests to fetch a customer profile using their email address.
    @GetMapping("/email/{email}")
    public Customer getByEmail(@PathVariable String email) {
        return customerService.getCustomerByEmail(email);
    }

    // ABSTRACTION: Handles GET requests to list all customer profiles.
    @GetMapping
    public List<Customer> getAllCustomers() {
        return customerService.getAllCustomers();
    }

    // ENCAPSULATION: Takes updated fields from the request body to modify an existing customer profile.
    @PutMapping("/{id}")
    public Customer updateCustomer(@PathVariable Long id, @RequestBody Customer customer) {
        return customerService.updateCustomer(id, customer);
    }

    // ENCAPSULATION: Securely updates profile details and handles optional password changes via DTO.
    @PutMapping("/{id}/profile")
    public Customer updateProfile(@PathVariable Long id, @RequestBody CustomerProfileDTO dto) {
        return customerService.updateProfile(id, dto);
    }

    // ABSTRACTION: Handles DELETE requests to remove a customer profile by ID.
    @DeleteMapping("/{id}")
    public String deleteCustomer(@PathVariable Long id) {
        customerService.deleteCustomer(id);
        return "Customer deleted successfully";
    }

    // ENCAPSULATION: Bundles profile update fields and password verification into one secure object.
    @Data
    public static class CustomerProfileDTO {
        private String firstName;
        private String lastName;
        private String email;
        private String phone;
        private String address;
        private String currentPassword;
        private String newPassword;
    }

    // ENCAPSULATION: Bundles all registration fields (User login info + Customer profile info) together.
    @Data
    public static class CustomerRegistrationDTO {
        private String username;
        private String password;
        private String firstName;
        private String lastName;
        private String email;
        private String phone;
        private String address;
    }
}
