package au.edu.rmit.sept.webapp.models;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import au.edu.rmit.sept.webapp.enums.UserType;

public class CustomUserTest {

    @Test
    public void testCustomUserGettersAndSetters() {
        CustomUser user = new CustomUser();

        user.setId(1L);
        user.setName("John Doe");
        user.setEmail("john.doe@example.com");
        user.setPassword("password123");
        user.setPhoneNumber("123-456-7890");
        user.setAddress("123 Main St");
        user.setUserType(UserType.PetOwner);

        assertEquals(1L, user.getUserId());
        assertEquals("John Doe", user.getName());
        assertEquals("john.doe@example.com", user.getEmail());
        assertEquals("password123", user.getPassword());
        assertEquals("123-456-7890", user.getPhoneNumber());
        assertEquals("123 Main St", user.getAddress());
        assertEquals(UserType.PetOwner, user.getUserType());
    }
}
