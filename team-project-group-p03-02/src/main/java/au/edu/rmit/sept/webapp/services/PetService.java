package au.edu.rmit.sept.webapp.services;

import au.edu.rmit.sept.webapp.dto.PetDTO;
import au.edu.rmit.sept.webapp.models.MedicalHistory;
import au.edu.rmit.sept.webapp.models.Medicine;
import au.edu.rmit.sept.webapp.models.Pet;
import au.edu.rmit.sept.webapp.models.Prescription;
import au.edu.rmit.sept.webapp.models.TreatmentPlan;
import au.edu.rmit.sept.webapp.models.VaccinationRecord;

import java.util.List;

import java.util.Optional;

public interface PetService {
    public List<Pet> getPets();
    public Optional<Pet> findPetBypetId(Long petId);
    public Pet getPetById(Long petId);
    public List<PetDTO> findPetsByUserId(Long userId);
    public void savePet(Pet pet);
    public void updatePet(Long petId, PetDTO petDTO);
    public void updatePrescriptions(Long petId, List<Prescription> prescriptions);
    public void updateTreatmentPlans(Long petId, List<TreatmentPlan> treatmentPlans);
    public void updateVaccinationRecords(Long petId, List<VaccinationRecord> vaccinations);
    void updateMedicalHistory(Long petId, MedicalHistory medicalHistory);
    public void saveMedicine(Medicine medicine);
  

}
