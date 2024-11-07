package au.edu.rmit.sept.webapp.models;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ClinicTest {

    @Test
    public void testClinicGettersAndSetters() {
        // Setup
        Clinic clinic = new Clinic();
        clinic.setClinicId(1L);
        clinic.setName("Health Clinic");
        clinic.setAddress("123 Main St");
        clinic.setPhoneNumber("123-456-7890");
        clinic.setEmail("info@healthclinic.com");

        // Assert
        assertEquals(1L, clinic.getClinicID());
        assertEquals("Health Clinic", clinic.getName());
        assertEquals("123 Main St", clinic.getAddress());
        assertEquals("123-456-7890", clinic.getPhoneNumber());
        assertEquals("info@healthclinic.com", clinic.getEmail());
    }
}
