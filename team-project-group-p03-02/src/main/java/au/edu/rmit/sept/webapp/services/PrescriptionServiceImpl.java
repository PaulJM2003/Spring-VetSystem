package au.edu.rmit.sept.webapp.services;

import au.edu.rmit.sept.webapp.models.Prescription;
import au.edu.rmit.sept.webapp.repositories.PrescriptionRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class PrescriptionServiceImpl implements PrescriptionService {

    private final PrescriptionRepository repository;

    @Autowired
    public PrescriptionServiceImpl(PrescriptionRepository repository) {
        this.repository = repository;
    }

    public Prescription getPrescriptionFromID(Long prescriptionID) {
        // Get prescription based on ID
        return repository.findByPrescriptionID(prescriptionID);
    }

    public String checkPrescription(Prescription prescription) {
        // Get current date
        LocalDate currentDate = LocalDate.now();
        // Check for remaining refills
        if (prescription.getRepeatsLeft() <= 0) {
            return "no repeats remaining";
        // Check for expiry
        } else if (prescription.getExpiryDate().isBefore(currentDate)) {
            return "out of date prescription";
        }
        // Prescription is valid
        return "valid";
    }

    public void decrementPrescription(Prescription prescription) {
        // Set repeats to (current - 1)
        prescription.setRepeatsLeft(prescription.getRepeatsLeft() - 1);
        // Save to repository
        repository.save(prescription);
    }

    public void orderPrescription(Long prescriptionID, Long petId) {
        // Handles prescription order requests
        // This is a feature that we do not plan to implement, so it's left as a placeholder.
        System.out.println(String.format("Recieved prescription order '%d' for pet '%d'", prescriptionID, petId));
    }
}
