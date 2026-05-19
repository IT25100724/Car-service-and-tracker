package com.example.car_service.customer;

import org.springframework.web.bind.annotation.*;  // This is needed for REST Controllers

import java.util.List;

// REST Controller for managing customer profiles and registrations
@RestController
@RequestMapping("/api/customers")
@CrossOrigin(origins = "*")
public class CustomerController {


    // Encapsulation (Attribute as Private)
    private final CustomerService customerService;


    // Constructor
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    // Create a new customer profile
    @PostMapping
    public Customer createCustomer(@RequestBody Customer customer) {
        return customerService.createCustomer(customer);
    }

    // Register a customer and set up their user credentials
    @PostMapping("/register")
    public Customer registerCustomer(@RequestBody CustomerRegistrationDTO registrationDTO) {
        return customerService.registerCustomer(registrationDTO);
    }

    // Retrieve customer details by ID
    @GetMapping("/{id}")
    public Customer getCustomerById(@PathVariable Long id) {
        return customerService.getCustomerById(id);
    }

    // Retrieve customer details by email
    @GetMapping("/email/{email}")
    public Customer getByEmail(@PathVariable String email) {
        return customerService.getCustomerByEmail(email);
    }

    // Retrieve all customer profiles
    @GetMapping
    public List<Customer> getAllCustomers() {
        return customerService.getAllCustomers();
    }

    // Update general customer profile info
    @PutMapping("/{id}")
    public Customer updateCustomer(@PathVariable Long id, @RequestBody Customer customer) {
        return customerService.updateCustomer(id, customer);
    }

    // Update detailed customer profile fields, including password
    @PutMapping("/{id}/profile")
    public Customer updateProfile(@PathVariable Long id, @RequestBody CustomerProfileDTO dto) {
        return customerService.updateProfile(id, dto);
    }

    // Delete a customer profile by ID
    @DeleteMapping("/{id}")
    public String deleteCustomer(@PathVariable Long id) {
        customerService.deleteCustomer(id);
        return "Customer deleted successfully";
    }

    // Data Transfer Object (DTO) for profile updates
    public static class CustomerProfileDTO {
        private String firstName;
        private String lastName;
        private String email;
        private String phone;
        private String address;
        private String currentPassword;
        private String newPassword;


        // Default constructor
        public CustomerProfileDTO() {
        }


        // Encapsulation (Below use getter/setter)
        public String getFirstName() {
            return firstName;
        }

        public void setFirstName(String firstName) {
            this.firstName = firstName;
        }

        public String getLastName() {
            return lastName;
        }

        public void setLastName(String lastName) {
            this.lastName = lastName;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getCurrentPassword() {
            return currentPassword;
        }

        public void setCurrentPassword(String currentPassword) {
            this.currentPassword = currentPassword;
        }

        public String getNewPassword() {
            return newPassword;
        }

        public void setNewPassword(String newPassword) {
            this.newPassword = newPassword;
        }
    }

    // Data Transfer Object (DTO) for customer self-registration
    public static class CustomerRegistrationDTO {
        private String username;
        private String password;
        private String firstName;
        private String lastName;
        private String email;
        private String phone;
        private String address;


        // Default Constructor
        public CustomerRegistrationDTO() {
        }


        // Encapsulation (Below use getter/setter)
        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getFirstName() {
            return firstName;
        }

        public void setFirstName(String firstName) {
            this.firstName = firstName;
        }

        public String getLastName() {
            return lastName;
        }

        public void setLastName(String lastName) {
            this.lastName = lastName;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }
    }
}
