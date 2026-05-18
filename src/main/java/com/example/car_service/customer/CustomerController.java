package com.example.car_service.customer;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/customers")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class CustomerController {

    // Encapsulation
    private final CustomerService customerService;

    // Polymorphism
    public ResponseEntity<Customer> createCustomer(@RequestBody Customer customer) {
        return new ResponseEntity<>(customerService.createCustomer(customer), HttpStatus.CREATED);
    }

    @PostMapping("/register")
    public ResponseEntity<Customer> registerCustomer(@RequestBody CustomerRegistrationDTO registrationDTO) {
        return new ResponseEntity<>(customerService.registerCustomer(registrationDTO), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Customer> getCustomerById(@PathVariable Long id) {
        return ResponseEntity.ok(customerService.getCustomerById(id));
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<Customer> getByEmail(@PathVariable String email) {
        return ResponseEntity.ok(customerService.getCustomerByEmail(email));
    }

    @GetMapping
    public ResponseEntity<List<Customer>> getAllCustomers() {
        return ResponseEntity.ok(customerService.getAllCustomers());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Customer> updateCustomer(@PathVariable Long id, @RequestBody Customer customer) {
        return ResponseEntity.ok(customerService.updateCustomer(id, customer));
    }

    @PutMapping("/{id}/profile")
    public ResponseEntity<?> updateProfile(@PathVariable Long id, @RequestBody CustomerProfileDTO dto) {
        try {
            Customer updated = customerService.updateProfile(id, dto);
            return ResponseEntity.ok(updated);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("message", e.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCustomer(@PathVariable Long id) {
        customerService.deleteCustomer(id);
        return ResponseEntity.ok("Customer deleted successfully");
    }

    // Customer profile DTO
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

    // Customer registration DTO
    @Data
    public static class CustomerRegistrationDTO {
        // User fields
        private String username;
        private String password;

        // Customer profile fields
        private String firstName;
        private String lastName;
        private String email;
        private String phone;
        private String address;
    }
}
