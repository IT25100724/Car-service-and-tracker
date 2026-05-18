package com.example.car_service.user;

import com.example.car_service.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    // ENCAPSULATION & ABSTRACTION: Validates that username/email are unique before saving the new user.
    public User createUser(User user) {
        if (userRepository.existsByUsername(user.getUsername())) {
            throw new RuntimeException("Username '" + user.getUsername() + "' is already taken.");
        }
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new RuntimeException("Email '" + user.getEmail() + "' is already registered.");
        }
        return userRepository.save(user);
    }

    // ABSTRACTION: Fetches a user by ID or throws a clean exception if not found.
    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
    }

    // ABSTRACTION: Fetches a user by username or throws a clean exception if not found.
    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with username: " + username));
    }

    // ABSTRACTION: Retrieves all user records from the database.
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    // ENCAPSULATION: Modifies the existing user's internal fields and saves the updated state.
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

    // ABSTRACTION: Ensures the user exists, then deletes them by ID.
    public void deleteUser(Long id) {
        getUserById(id);
        userRepository.deleteById(id);
    }
}
