package au.edu.rmit.sept.webapp.services;

import au.edu.rmit.sept.webapp.models.MedicalHistory;
import au.edu.rmit.sept.webapp.models.Prescription;
import au.edu.rmit.sept.webapp.models.TreatmentPlan;
import au.edu.rmit.sept.webapp.models.VaccinationRecord;
import au.edu.rmit.sept.webapp.repositories.MedicalHistoryRepository;
import au.edu.rmit.sept.webapp.repositories.PrescriptionRepository;
import au.edu.rmit.sept.webapp.repositories.TreatmentPlanRepository;
import au.edu.rmit.sept.webapp.repositories.VaccinationRecordRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PetMedicalHistoryServiceTest {

    @Mock
    private MedicalHistoryRepository medicalHistoryRepository;

    @Mock
    private VaccinationRecordRepository vaccinationRecordRepository;

    @Mock
    private TreatmentPlanRepository treatmentPlanRepository;

    @Mock
    private PrescriptionRepository prescriptionRepository;

    @InjectMocks
    private PetMedicalHistoryService petMedicalHistoryService;

    private MedicalHistory medicalHistory;
    private VaccinationRecord vaccinationRecord;
    private TreatmentPlan treatmentPlan;
    private Prescription prescription;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Set up sample objects
        medicalHistory = new MedicalHistory();
        vaccinationRecord = new VaccinationRecord();
        treatmentPlan = new TreatmentPlan();
        prescription = new Prescription();
    }

    @Test
    void testGetMedicalHistoryBypetId_Found() {
        // Mocking
        when(medicalHistoryRepository.findByPet_petId(anyLong())).thenReturn(Optional.of(medicalHistory));

        // Call the service method
        Optional<MedicalHistory> result = petMedicalHistoryService.getMedicalHistoryBypetId(1L);

        // Verifying the behavior and result
        assertTrue(result.isPresent());
        assertEquals(medicalHistory, result.get());
    }

    @Test
    void testGetMedicalHistoryBypetId_NotFound() {
        // Mocking an empty result
        when(medicalHistoryRepository.findByPet_petId(anyLong())).thenReturn(Optional.empty());

        // Call the service method
        Optional<MedicalHistory> result = petMedicalHistoryService.getMedicalHistoryBypetId(1L);

        // Verifying that the result is empty
        assertFalse(result.isPresent());
    }

    @Test
    void testGetVaccinationRecordsBypetId() {
        // Sample list
        List<VaccinationRecord> vaccinationRecords = new ArrayList<>();
        vaccinationRecords.add(vaccinationRecord);

        // Mocking
        when(vaccinationRecordRepository.findByPet_petId(anyLong())).thenReturn(vaccinationRecords);

        // Call the service method
        List<VaccinationRecord> result = petMedicalHistoryService.getVaccinationRecordsBypetId(1L);

        // Verifying the behavior and result
        assertEquals(1, result.size());
        assertEquals(vaccinationRecord, result.get(0));
    }

    @Test
    void testGetTreatmentPlansBypetId() {
        // Sample list
        List<TreatmentPlan> treatmentPlans = new ArrayList<>();
        treatmentPlans.add(treatmentPlan);

        // Mocking
        when(treatmentPlanRepository.findByPet_petId(anyLong())).thenReturn(treatmentPlans);

        // Call the service method
        List<TreatmentPlan> result = petMedicalHistoryService.getTreatmentPlansBypetId(1L);

        // Verifying the behavior and result
        assertEquals(1, result.size());
        assertEquals(treatmentPlan, result.get(0));
    }

    @Test
    void testGetPrescriptionsBypetId() {
        // Sample list
        List<Prescription> prescriptions = new ArrayList<>();
        prescriptions.add(prescription);

        // Mocking
        when(prescriptionRepository.findByPet_petId(anyLong())).thenReturn(prescriptions);

        // Call the service method
        List<Prescription> result = petMedicalHistoryService.getPrescriptionsBypetId(1L);

        // Verifying the behavior and result
        assertEquals(1, result.size());
        assertEquals(prescription, result.get(0));
    }
}