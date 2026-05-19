package com.example.car_service.user;

import org.springframework.web.bind.annotation.*;   // Import bringing in all Spring MVC annotations

import java.util.List; 

// REST Controller for managing user accounts
@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "*")
public class UserController {

    // Encapsulation (Attribute as Private)
    private final UserService userService;


    // Constructor
    public UserController(UserService userService) {
        this.userService = userService;
    }

    // Create a new user account
    @PostMapping
    public User createUser(@RequestBody User user) {
        return userService.createUser(user);
    }

    // Retrieve user account by ID
    @GetMapping("/{id}")
    public User getUserById(@PathVariable Long id) {
        return userService.getUserById(id);
    }

    // Retrieve all user accounts
    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    // Update user account details
    @PutMapping("/{id}")
    public User updateUser(@PathVariable Long id, @RequestBody User user) {
        return userService.updateUser(id, user);
    }

    // Delete a user account by ID
    @DeleteMapping("/{id}")
    public String deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return "User deleted successfully";
    }
}
