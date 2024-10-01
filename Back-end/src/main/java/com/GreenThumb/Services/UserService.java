package com.GreenThumb.Services;

import com.GreenThumb.DTO.UserDTO;
import com.GreenThumb.Exceptions.ResourceNotFoundException;
import com.GreenThumb.Mappers.UserMapper;
import com.GreenThumb.Models.heritage.Client;
import com.GreenThumb.Models.heritage.User;
import com.GreenThumb.Repositories.UserRepository; // Make sure you have a UserRepository
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMapper userMapper;

    // Method to create a new User
    public User createUser(UserDTO userDTO) {
        User user = userMapper.toEntity(userDTO); // Map DTO to entity
        return userRepository.save(user); // Save the user to the repository
    }

    // Method to retrieve all Users
    public List<User> getAllUsers() {
        List<User> users = userRepository.findAll();
        if (users.isEmpty()) {
            throw new ResourceNotFoundException();
        }
        return users;
    }

    // Method to retrieve a User by ID
    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(ResourceNotFoundException::new);
    }

    // Method to update an existing User
    public User updateUser(Long id, UserDTO userDTO) {
        User existingUser = userRepository.findById(id)
                .orElseThrow(ResourceNotFoundException::new);

        // Update fields using the mapper for partial update
        userMapper.partialUpdate(userDTO, existingUser);
        return userRepository.save(existingUser); // Save the updated user
    }

    // Method to delete a User
    public void deleteUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(ResourceNotFoundException::new);
        userRepository.delete(user);
    }

    // Method to retrieve Users by Role
    public List<User> getByRole(String role) {
        List<User> usersByRole = userRepository.findByRole(role); // This method should be defined in UserRepository
        if (usersByRole.isEmpty()) {
            throw new ResourceNotFoundException();
        }
        return usersByRole;
    }
}
