package au.edu.rmit.sept.webapp.services;

import au.edu.rmit.sept.webapp.models.Prescription;

public interface PrescriptionService {
    public Prescription getPrescriptionFromID(Long prescriptionID);
    public String checkPrescription(Prescription prescription);
    public void decrementPrescription(Prescription prescription);
    public void orderPrescription(Long prescriptionID, Long petId);
}
