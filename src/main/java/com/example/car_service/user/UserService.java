package com.example.car_service.user;

import com.example.car_service.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import java.util.List;

// Service class for managing user all logic and CRUD operations
@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // Create a user, checking for duplicate username and email first
    public User createUser(User user) {
        if (userRepository.existsByUsername(user.getUsername())) {
            throw new RuntimeException("Username '" + user.getUsername() + "' is already taken.");
        }
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new RuntimeException("Email '" + user.getEmail() + "' is already registered.");
        }
        return userRepository.save(user);
    }

    // Find a user by ID or not > exception if not found
    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
    }

    // Find a user by username or not > exception if not found
    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with username: " + username));
    }

    // Retrieve all users
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    // Update user properties, including password if provided
    public User updateUser(Long id, User user) {
        User existingUser = getUserById(id);

        existingUser.setUsername(user.getUsername());
        existingUser.setEmail(user.getEmail());
        existingUser.setRole(user.getRole());
        existingUser.setIsActive(user.getIsActive());

        if (user.getPasswordHash() != null && !user.getPasswordHash().isEmpty()) {
            existingUser.setPasswordHash(user.getPasswordHash());
        }

        return userRepository.save(existingUser);
    }

    // Delete a user by ID, checking existence first
    public void deleteUser(Long id) {
        getUserById(id);
        userRepository.deleteById(id);
    }
}
