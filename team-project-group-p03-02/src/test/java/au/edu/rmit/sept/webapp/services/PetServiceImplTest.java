package au.edu.rmit.sept.webapp.services;

import au.edu.rmit.sept.webapp.dto.PetDTO;
import au.edu.rmit.sept.webapp.models.Pet;
import au.edu.rmit.sept.webapp.repositories.PetRepository;
import au.edu.rmit.sept.webapp.repositories.PrescriptionRepository;
import au.edu.rmit.sept.webapp.repositories.TreatmentPlanRepository;
import au.edu.rmit.sept.webapp.repositories.VaccinationRecordRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

class PetServiceImplTest {

    @Mock
    private PetRepository petRepository;

    @Mock
    private VaccinationRecordRepository vaccinationRecordRepository;

    @Mock
    private PrescriptionRepository prescriptionRepository;

    @Mock
    private TreatmentPlanRepository treatmentPlanRepository;

    @InjectMocks
    private PetServiceImpl petService;

    private Pet pet;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Sample Pet object
        pet = new Pet();
        pet.setPetId(1L);
        pet.setName("Buddy");
    }

    @Test
    void testGetPetById_Found() {
        // Mocking
        when(petRepository.findById(anyLong())).thenReturn(Optional.of(pet));

        // Call the service method
        Pet result = petService.getPetById(1L);

        // Verifying the behavior and result
        assertNotNull(result);
        assertEquals("Buddy", result.getName());
    }

    @Test
    void testGetPetById_NotFound() {
        // Mocking a case where the pet is not found
        when(petRepository.findById(anyLong())).thenReturn(Optional.empty());

        // Asserting that an exception is thrown
        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> {
            petService.getPetById(1L);
        });

        assertEquals("Pet not found with ID: 1", exception.getMessage());
    }

    @Test
    void testSavePet() {
        // Call the save method
        petService.savePet(pet);

        // Verify the save operation
        verify(petRepository, times(1)).save(pet);
    }

    @Test
    void testFindPetBypetId_Found() {
        // Mocking
        when(petRepository.findById(anyLong())).thenReturn(Optional.of(pet));

        // Call the service method
        Optional<Pet> foundPet = petService.findPetBypetId(1L);

        // Verify
        assertTrue(foundPet.isPresent());
        assertEquals(pet.getPetId(), foundPet.get().getPetId());
    }

    @Test
    void testFindPetBypetId_NotFound() {
        // Mocking an empty result
        when(petRepository.findById(anyLong())).thenReturn(Optional.empty());

        // Call the service method
        Optional<Pet> foundPet = petService.findPetBypetId(1L);

        // Verify
        assertFalse(foundPet.isPresent());
    }
}