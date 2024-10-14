package com.GreenThumb.Service;

import com.GreenThumb.DTO.UserDTO;
import com.GreenThumb.Exceptions.ResourceNotFoundException;
import com.GreenThumb.Mappers.UserMapper;
import com.GreenThumb.Models.Enums.Role;
import com.GreenThumb.Models.heritage.User;
import com.GreenThumb.Repositories.UserRepository;
import com.GreenThumb.Services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateUser() {
        // Arrange
        UserDTO userDTO = new UserDTO();
        User user = new User();

        when(userMapper.toEntity(userDTO)).thenReturn(user);
        when(userRepository.save(user)).thenReturn(user);

        // Act
        User createdUser = userService.createUser(userDTO);

        // Assert
        assertNotNull(createdUser);
        verify(userRepository, times(1)).save(user);
    }

    @Test
    void testGetAllUsers() {
        // Arrange
        User user1 = new User();
        User user2 = new User();
        when(userRepository.findAll()).thenReturn(List.of(user1, user2));

        // Act
        List<User> allUsers = userService.getAllUsers();

        // Assert
        assertNotNull(allUsers);
        assertEquals(2, allUsers.size());
    }

    @Test
    void testGetUserById() {
        // Arrange
        Long userId = 1L;
        User user = new User();
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        // Act
        User foundUser = userService.getUserById(userId);

        // Assert
        assertNotNull(foundUser);
        assertEquals(user, foundUser);
    }

    @Test
    void testUpdateUser() {
        // Arrange
        Long userId = 1L;
        UserDTO userDTO = new UserDTO();
        User existingUser = new User();
        User updatedUser = new User();

        when(userRepository.findById(userId)).thenReturn(Optional.of(existingUser));
        when(userMapper.partialUpdate(userDTO, existingUser)).thenReturn(updatedUser);
        when(userRepository.save(updatedUser)).thenReturn(updatedUser);

        // Act
        User result = userService.updateUser(userId, userDTO);

        // Assert
        assertNotNull(result);
        verify(userRepository, times(1)).save(updatedUser);
    }

    @Test
    void testDeleteUser() {
        // Arrange
        Long userId = 1L;
        User user = new User();
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        // Act
        userService.deleteUser(userId);

        // Assert
        verify(userRepository, times(1)).delete(user);
    }

    @Test
    void testGetByRole() {
        // Arrange
        Role role = Role.ADMIN; // Use the enum for role
        User user1 = new User();
        User user2 = new User();
        when(userRepository.findByRole(role)).thenReturn(List.of(user1, user2));

        // Act
        List<User> usersByRole = userService.getByRole(role);

        // Assert
        assertNotNull(usersByRole);
        assertEquals(2, usersByRole.size());
    }
}
