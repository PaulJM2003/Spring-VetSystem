package au.edu.rmit.sept.webapp.models;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Date;

class DosageTest {

    // Test Case 1: Verify object creation and field access
    @Test
    void testDosageCreation() {
        Pet pet = new Pet();
        Medicine medicine = new Medicine();
        String dosageQuantity = "5ml";
        String instructions = "Twice a day";
        Date dateAdministered = new Date();
        Date nextDosageDate = new Date();
        Dosage dosage = new Dosage();
        dosage.setPet(pet);
        dosage.setMedicine(medicine);
        dosage.setDosageQuantity(dosageQuantity);
        dosage.setInstructions(instructions);
        dosage.setDateAdministered(dateAdministered);
        dosage.setNextDosageDate(nextDosageDate);
        assertEquals(pet, dosage.getPet());
        assertEquals(medicine, dosage.getMedicine());
        assertEquals(dosageQuantity, dosage.getDosageQuantity());
        assertEquals(instructions, dosage.getInstructions());
        assertEquals(dateAdministered, dosage.getDateAdministered());
        assertEquals(nextDosageDate, dosage.getNextDosageDate());
    }
    // Test Case 2: Ensure proper behavior when setting and retrieving dosage ID
    @Test
    void testDosageId() {
        Dosage dosage = new Dosage();
        Long dosageId = 100L;
        dosage.setDosageId(dosageId);
        assertEquals(dosageId, dosage.getDosageId());
    }
    // Test Case 3: Ensure two Dosage objects with identical data are not considered equal
    @Test
    void testDosageInequality() {
        Dosage dosage1 = new Dosage();
        dosage1.setDosageId(1L);
        dosage1.setDosageQuantity("5ml");
        
        Dosage dosage2 = new Dosage();
        dosage2.setDosageId(2L);
        dosage2.setDosageQuantity("5ml");
        assertNotEquals(dosage1, dosage2); 
    }
    // Test Case 4: Verify that setting the pet and medicine in Dosage works correctly
    @Test
    void testSetPetAndMedicine() {
        Dosage dosage = new Dosage();
        Pet pet = new Pet();
        Medicine medicine = new Medicine();
        dosage.setPet(pet);
        dosage.setMedicine(medicine);
        assertEquals(pet, dosage.getPet());
        assertEquals(medicine, dosage.getMedicine());
    }    
    // Test Case 5: Verify date handling for administered and next dosage dates
    @Test
    void testDosageDates() {
        Dosage dosage = new Dosage();
        Date dateAdministered = new Date();
        Date nextDosageDate = new Date();
        dosage.setDateAdministered(dateAdministered);
        dosage.setNextDosageDate(nextDosageDate);
        assertEquals(dateAdministered, dosage.getDateAdministered());
        assertEquals(nextDosageDate, dosage.getNextDosageDate());
    }
    // Test Case 6: Ensure dosage quantity and instructions can be set and retrieved correctly
    @Test
    void testDosageDetails() {
        Dosage dosage = new Dosage();
        String dosageQuantity = "10mg";
        String instructions = "Take once before bed";
        dosage.setDosageQuantity(dosageQuantity);
        dosage.setInstructions(instructions);
        assertEquals(dosageQuantity, dosage.getDosageQuantity());
        assertEquals(instructions, dosage.getInstructions());
    }
}
