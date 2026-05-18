package com.example.car_service.user;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class UserController {

    // ABSTRACTION: Use the UserService to handle business logic, hiding the inner details.
    private final UserService userService;

    // ENCAPSULATION: Receives a User object in the request body and passes it to the service to be saved.
    @PostMapping
    public User createUser(@RequestBody User user) {
        return userService.createUser(user);
    }

    // ABSTRACTION: Handles GET requests to fetch a specific user by their ID.
    @GetMapping("/{id}")
    public User getUserById(@PathVariable Long id) {
        return userService.getUserById(id);
    }

    // ABSTRACTION: Handles GET requests to retrieve a list of all users in the system.
    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    // ENCAPSULATION: Takes updated fields from the request body to modify an existing user account.
    @PutMapping("/{id}")
    public User updateUser(@PathVariable Long id, @RequestBody User user) {
        return userService.updateUser(id, user);
    }

    // ABSTRACTION: Handles DELETE requests to remove a user by their ID.
    @DeleteMapping("/{id}")
    public String deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return "User deleted successfully";
    }
}
