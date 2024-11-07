package au.edu.rmit.sept.webapp.services;

import au.edu.rmit.sept.webapp.dto.DosageDTO;
import au.edu.rmit.sept.webapp.repositories.DosageRepository;
import jakarta.persistence.criteria.CriteriaBuilder.In;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

class DosageServiceTest {

    @Mock
    private DosageRepository dosageRepository;

    @InjectMocks
    private DosageService dosageService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    // Test Case 1: Test if the correct list of dosage details is returned for a given pet and medicine ID.
    @Test
    void testGetDetailedDosageByPetIdAndMedicineId_Success() {
        List<DosageDTO> mockDosages = new ArrayList<>();
        mockDosages.add(new DosageDTO("Medicine A", "5 mg", "Take with food", new Date(), new Date(), "No side effects"));
        mockDosages.add(new DosageDTO("Medicine B", "10 mg", "Take before bed", new Date(), new Date(), "Drowsiness"));
        when(dosageRepository.findDetailedDosageByPetIdAndMedicineId(anyLong(), anyLong())).thenReturn(mockDosages);
        List<DosageDTO> result = dosageService.getDetailedDosageByPetIdAndMedicineId(1L, 1L);
        assertEquals(2, result.size());
        assertEquals("5 mg", result.get(0).getDosageQuantity()); 
        assertEquals("Take with food", result.get(0).getInstructions()); 
        assertEquals("10 mg", result.get(1).getDosageQuantity()); 
        assertEquals("Take before bed", result.get(1).getInstructions()); 
    }
    // Test Case 2: Test if an empty list is returned when there are no dosage details for a given pet and medicine ID.
    @Test
    void testGetDetailedDosageByPetIdAndMedicineId_EmptyList() {
        when(dosageRepository.findDetailedDosageByPetIdAndMedicineId(anyLong(), anyLong())).thenReturn(new ArrayList<>());
        List<DosageDTO> result = dosageService.getDetailedDosageByPetIdAndMedicineId(1L, 1L);
        assertEquals(0, result.size());
    }
    // Test Case 3: Test if the correct behavior occurs when the repository returns null.
    // just handling this case for safety.
    @Test
    void testGetDetailedDosageByPetIdAndMedicineId_NullResponse() {
        when(dosageRepository.findDetailedDosageByPetIdAndMedicineId(anyLong(), anyLong())).thenReturn(null);
        List<DosageDTO> result = dosageService.getDetailedDosageByPetIdAndMedicineId(1L, 1L);
        assertTrue(result == null || result.isEmpty(), "The service should return an empty list or null safely.");
    }
}
