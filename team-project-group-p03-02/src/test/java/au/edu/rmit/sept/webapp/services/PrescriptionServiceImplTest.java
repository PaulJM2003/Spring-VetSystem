package au.edu.rmit.sept.webapp.services;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import au.edu.rmit.sept.webapp.models.Prescription;
import au.edu.rmit.sept.webapp.repositories.PrescriptionRepository;

class PrescriptionServiceImplTest {

    @Mock
    private PrescriptionRepository prescriptionRepository;

    @InjectMocks
    private PrescriptionServiceImpl prescriptionService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        
    }

    @Test
    void should_checkValidPrescription_when_TomorrowPresctiptionModelIsGiven() {
        // Prepare Localdate variable
        LocalDate dateTomorrow = LocalDate.now().plusDays(1);

        // Create a tomorrow test prescription model
        Prescription prescription = new Prescription();
        prescription.setRepeatsLeft(2);
        prescription.setExpiryDate(dateTomorrow);

        // Check if prescription is valid
        assertEquals("valid", prescriptionService.checkPrescription(prescription));
    }

    @Test
    void should_checkValidPrescription_when_TodayPresctiptionModelIsGiven() {
        // Prepare Localdate variable
        LocalDate dateToday = LocalDate.now();

        // Create a today test prescription model
        Prescription prescription = new Prescription();
        prescription.setRepeatsLeft(2);
        prescription.setExpiryDate(dateToday);

        // Check if prescription is valid
        assertEquals("valid", prescriptionService.checkPrescription(prescription));
    }

    @Test
    void should_checkInvalidPrescription_when_YesterdayPresctiptionModelIsGiven() {
        // Prepare Localdate variable
        LocalDate dateYesterday = LocalDate.now().minusDays(1);

        // Create a yesterday test prescription model
        Prescription prescription = new Prescription();
        prescription.setRepeatsLeft(2);
        prescription.setExpiryDate(dateYesterday);

        // Check if prescription is valid
        assertEquals("out of date prescription", prescriptionService.checkPrescription(prescription));
    }

    @Test
    void should_checkInvalidPrescription_when_ZeroRepeatsIsGiven() {
        // Prepare Localdate variable
        LocalDate dateTomorrow = LocalDate.now().plusDays(1);

        // Create a tomorrow test prescription model
        Prescription prescription = new Prescription();
        prescription.setRepeatsLeft(0);
        prescription.setExpiryDate(dateTomorrow);

        // Check if prescription is valid
        assertEquals("no repeats remaining", prescriptionService.checkPrescription(prescription));
    }

    @Test
    void should_checkInvalidPrescription_when_NegativeRepeatsIsGiven() {
        // Prepare Localdate variable
        LocalDate dateTomorrow = LocalDate.now().plusDays(1);

        // Create a tomorrow test prescription model
        Prescription prescription = new Prescription();
        prescription.setRepeatsLeft(-1);
        prescription.setExpiryDate(dateTomorrow);

        // Check if prescription is valid
        assertEquals("no repeats remaining", prescriptionService.checkPrescription(prescription));
    }

    @Test
    void should_DecrementPrescription_when_PrescriptionModelIsGiven() {
        // Create a test prescription model
        Prescription prescription = new Prescription();
        prescription.setRepeatsLeft(2);

        // Decrement
        prescriptionService.decrementPrescription(prescription);

        // Check if prescription has been decremented correctly
        assertEquals(1, prescription.getRepeatsLeft());
    }
}
