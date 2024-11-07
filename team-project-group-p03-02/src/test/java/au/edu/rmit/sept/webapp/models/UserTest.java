package au.edu.rmit.sept.webapp.models;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class UserTest {

    // Test Case 1: Verify object creation and field access
    @Test
    void testUserCreation() {
        Long userId = 1L;
        String name = "Cristiano Sui";
        String password = "password123";
        String email = "cristiano.sui@example.com";
        String phoneNumber = "123456789";
        String address = "123 Stadium Road, City, Country";        
        User user = new User(userId, name, password, email, phoneNumber, address);
        assertEquals(userId, user.userID());
        assertEquals(name, user.name());
        assertEquals(password, user.password());
        assertEquals(email, user.email());
        assertEquals(phoneNumber, user.phoneNumber());
        assertEquals(address, user.address());
    }
    // Test Case 2: Ensure equality between two identical User objects
    @Test
    void testUserEquality() {
        User user1 = new User(1L, "Cristiano Sui", "password123", "cristiano.sui@example.com", "123456789", "123 Stadium Road, City, Country");
        User user2 = new User(1L, "Cristiano Sui", "password123", "cristiano.sui@example.com", "123456789", "123 Stadium Road, City, Country");        
        assertEquals(user1, user2); 
        assertEquals(user1.hashCode(), user2.hashCode()); 
    }
    // Test Case 3: Verify that User objects with different data are not equal
    @Test
    void testUserInequality() {
        User user1 = new User(1L, "Cristiano Sui", "password123", "cristiano.sui@example.com", "123456789", "123 Stadium Road, City, Country");
        User user2 = new User(2L, "Lionel Messi", "password123", "lionel.messi@example.com", "987654321", "456 Avenue, City, Country");        
        assertNotEquals(user1, user2); 
    }
    // Test Case 4: Confirm immutability of the User record
    @Test
    void testUserImmutability() {
        User user = new User(1L, "Cristiano Sui", "password123", "cristiano.sui@example.com", "123456789", "123 Stadium Road, City, Country");
        assertEquals(1L, user.userID());
        assertEquals("Cristiano Sui", user.name());
        assertEquals("password123", user.password());
        assertEquals("cristiano.sui@example.com", user.email());
        assertEquals("123456789", user.phoneNumber());
        assertEquals("123 Stadium Road, City, Country", user.address());
    }
}
