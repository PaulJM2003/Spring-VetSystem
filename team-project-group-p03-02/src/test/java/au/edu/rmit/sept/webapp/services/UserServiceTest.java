package au.edu.rmit.sept.webapp.services;

import au.edu.rmit.sept.webapp.models.User;
import au.edu.rmit.sept.webapp.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import java.util.ArrayList;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    private User mockUser;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockUser = new User(1L, "testuser", "encodedPassword", "testuser@example.com", "1234567890", "Test Address");
    }

    // Test Case 1: Ensure loadUserByUsername throws exception if user not found
    @Test
    void testLoadUserByUsername_UserNotFound() {
        when(userRepository.findByName("testuser")).thenReturn(Optional.empty());
        assertThrows(UsernameNotFoundException.class, () -> userService.loadUserByUsername("testuser"));
    }
    // Test Case 2: Ensure loadUserByUsername returns UserDetails when user exists
    @Test
    void testLoadUserByUsername_UserFound() {
        when(userRepository.findByName("testuser")).thenReturn(Optional.of(mockUser));
        var userDetails = userService.loadUserByUsername("testuser");
        assertEquals("testuser", userDetails.getUsername());
        assertEquals("encodedPassword", userDetails.getPassword());
        assertTrue(userDetails.getAuthorities().isEmpty());
    }
    // Test Case 3: Ensure getUserById returns the correct user when found
    @Test
    void testGetUserById_UserFound() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(mockUser));
        var result = userService.getUserById(1L);
        assertTrue(result.isPresent());
        assertEquals(mockUser, result.get());
    }
    // Test Case 4: Ensure getUserIdByUsername throws exception if user not found
    @Test
    void testGetUserIdByUsername_UserNotFound() {
        when(userRepository.findByName("nonexistent")).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> userService.getUserIdByUsername("nonexistent"));
    }
    // Test Case 5: Ensure getUserIdByUsername returns user ID when user is found
    @Test
    void testGetUserIdByUsername_UserFound() {
        when(userRepository.findByName("testuser")).thenReturn(Optional.of(mockUser));
        Long userID = userService.getUserIdByUsername("testuser");
        assertEquals(1L, userID);
    }
    // Test Case 6: Ensure updateContactInfo updates user information correctly
    @Test
    void testUpdateContactInfo_Success() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(mockUser));
        User updatedUser = new User(1L, "newname", "encodedPassword", "newemail@example.com", "9876543210", "New Address");
        User result = userService.updateContactInfo(1L, updatedUser);
        assertEquals("newname", result.name());
        assertEquals("newemail@example.com", result.email());
        assertEquals("9876543210", result.phoneNumber());
        assertEquals("New Address", result.address());
    }
    // Test Case 7: Ensure updateContactInfo throws exception when user not found
    @Test
    void testUpdateContactInfo_UserNotFound() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> userService.updateContactInfo(1L, mockUser));
    }
    // Test Case 8: Ensure changePassword returns true on successful password change
    @Test
    void testChangePassword_Success() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(mockUser));
        when(passwordEncoder.matches("oldPassword", mockUser.password())).thenReturn(true);
        when(passwordEncoder.encode("newPassword")).thenReturn("newEncodedPassword");
        boolean result = userService.changePassword(1L, "oldPassword", "newPassword");
        assertTrue(result);
        verify(userRepository).updatePassword(1L, "newEncodedPassword");
    }
    // Test Case 9: Ensure changePassword returns false if old password doesn't match
    @Test
    void testChangePassword_OldPasswordMismatch() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(mockUser));
        when(passwordEncoder.matches("wrongPassword", mockUser.password())).thenReturn(false);
        boolean result = userService.changePassword(1L, "wrongPassword", "newPassword");
        assertFalse(result);
    }
    // Test Case 10: Ensure deleteAccount deletes user if password matches
    @Test
    void testDeleteAccount_Success() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(mockUser));
        when(passwordEncoder.matches("password", mockUser.password())).thenReturn(true);
        boolean result = userService.deleteAccount(1L, "password");
        assertTrue(result);
        verify(userRepository).delete(1L);
    }
    // Test Case 11: Ensure deleteAccount returns false if password doesn't match
    @Test
    void testDeleteAccount_PasswordMismatch() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(mockUser));
        when(passwordEncoder.matches("wrongPassword", mockUser.password())).thenReturn(false);
        boolean result = userService.deleteAccount(1L, "wrongPassword");
        assertFalse(result);
        verify(userRepository, never()).delete(anyLong()); 
    }
}
