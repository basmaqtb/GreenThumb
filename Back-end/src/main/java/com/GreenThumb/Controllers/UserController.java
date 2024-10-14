package com.GreenThumb.Controllers;

import com.GreenThumb.DTO.UserDTO; // Changed to UserDTO
import com.GreenThumb.Exceptions.ResourceNotFoundException;
import com.GreenThumb.Mappers.UserMapper; // Changed to UserMapper
import com.GreenThumb.Models.Enums.Role;
import com.GreenThumb.Models.heritage.User; // Changed to User
import com.GreenThumb.Services.UserService; // Changed to UserService
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/auth/users") // Changed to "/users"
@CrossOrigin(origins = "*")
public class UserController {

    @Autowired
    private UserService userService; // Changed to UserService

    @Autowired
    private UserMapper userMapper; // Changed to UserMapper

    @GetMapping
    public ResponseEntity<List<UserDTO>> getAllUsers() { // Changed to UserDTO
        List<User> users = userService.getAllUsers(); // Changed to User
        return ResponseEntity.ok(userMapper.toDto(users)); // Changed to UserMapper
    }

    @PostMapping("/add")
    public ResponseEntity<UserDTO> createUser(@RequestBody UserDTO userDTO) { // Changed to UserDTO
        User createdUser = userService.createUser(userDTO); // Changed to User
        return ResponseEntity.status(HttpStatus.CREATED).body(userMapper.toDto(createdUser)); // Changed to UserMapper
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable("id") Long id) { // Changed to UserDTO
        User user = userService.getUserById(id); // Changed to User
        UserDTO userDTO = userMapper.toDto(user); // Changed to UserDTO
        return ResponseEntity.ok(userDTO); // Changed to UserDTO
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<UserDTO> updateUser(@PathVariable Long id, @RequestBody UserDTO updatedUserDTO) { // Changed to UserDTO
        try {
            User updatedUser = userService.updateUser(id, updatedUserDTO); // Changed to User
            return ResponseEntity.ok(userMapper.toDto(updatedUser)); // Changed to UserMapper
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) { // Changed to User
        try {
            userService.deleteUser(id); // Changed to UserService
            return ResponseEntity.noContent().build();  // Returns a 204 No Content status
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();  // Returns a 404 Not Found status if the user is not found
        }
    }

    // New method to get users by role
    @GetMapping("/role/{role}")
    public ResponseEntity<List<UserDTO>> getUsersByRole(@PathVariable Role role) {
        List<User> usersByRole = userService.getByRole(role);
        return ResponseEntity.ok(userMapper.toDto(usersByRole));
    }

    @GetMapping("/jardiniers")
    public ResponseEntity<List<UserDTO>> getAllJardiniers() {
        List<User> jardiniers = userService.getAllJardiniers(); // Utilisation de la méthode que vous avez ajoutée
        return ResponseEntity.ok(userMapper.toDto(jardiniers));
    }

    // New method to get all Clients
    @GetMapping("/clients")
    public ResponseEntity<List<UserDTO>> getAllClients() {
        List<User> clients = userService.getAllClients(); // Utilisation de la méthode que vous avez ajoutée
        return ResponseEntity.ok(userMapper.toDto(clients));
    }
}
