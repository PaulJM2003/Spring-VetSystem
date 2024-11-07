package au.edu.rmit.sept.webapp.services;

import au.edu.rmit.sept.webapp.dto.PetDTO;
import au.edu.rmit.sept.webapp.models.*;
import au.edu.rmit.sept.webapp.repositories.*;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PetServiceImpl implements PetService {

    private final PetRepository petRepository;
    private final VaccinationRecordRepository vaccinationRecordRepository;
    private final PrescriptionRepository prescriptionRepository;
    private final TreatmentPlanRepository treatmentPlanRepository;
    private final MedicalHistoryRepository medicalHistoryRepository;
    private final MedicineRepository medicineRepository;  


    @Autowired
    public PetServiceImpl(PetRepository petRepository, 
                          VaccinationRecordRepository vaccinationRecordRepository, 
                          PrescriptionRepository prescriptionRepository, 
                          TreatmentPlanRepository treatmentPlanRepository,
                          MedicalHistoryRepository medicalHistoryRepository,
                          MedicineRepository medicineRepository) {  // Include MedicineRepository in constructor
        this.petRepository = petRepository;
        this.vaccinationRecordRepository = vaccinationRecordRepository;
        this.prescriptionRepository = prescriptionRepository;
        this.treatmentPlanRepository = treatmentPlanRepository;
        this.medicalHistoryRepository = medicalHistoryRepository;
        this.medicineRepository = medicineRepository;  // Initialize it here
    }

    public List<Pet> getPets() {
        return petRepository.findAll();
    }

    @Override
    public Optional<Pet> findPetBypetId(Long petId) {
        return petRepository.findById(petId);
    }

    @Override
    public Pet getPetById(Long petId) {
        return petRepository.findById(petId)
            .orElseThrow(() -> new EntityNotFoundException("Pet not found with ID: " + petId));
    }

    @Override
    public List<PetDTO> findPetsByUserId(Long userId) {
        List<Pet> pets = petRepository.findByOwnerUserId(userId);
        List<PetDTO> petDTOs = new ArrayList<>();

        for (Pet pet : pets) {
            PetDTO petDTO = new PetDTO(pet);

            // Calculate last vaccination date
            Optional<LocalDate> lastVaccinationDateOptional = vaccinationRecordRepository.findLatestVaccinationDateByPetId(pet.getPetId());
            petDTO.setLastVaccinationDate(lastVaccinationDateOptional.orElse(null));

            // Check for active prescriptions
            boolean hasActivePrescriptions = prescriptionRepository.existsByPet_PetIdAndExpiryDateAfter(pet.getPetId(), LocalDate.now());
            petDTO.setActivePrescriptions(hasActivePrescriptions);

            // Check for active treatment plans
            boolean hasActiveTreatments = treatmentPlanRepository.existsByPet_PetIdAndEndDateAfter(pet.getPetId(), LocalDate.now());
            petDTO.setActiveTreatments(hasActiveTreatments);

            petDTOs.add(petDTO);
        }

        return petDTOs;
    }

    @Override
    public void savePet(Pet pet) {
        petRepository.save(pet);
    }

    public void saveMedicine(Medicine medicine) {
        medicineRepository.save(medicine);  // Save medicine to the database
    }

    // Update basic information of a pet (name, species, breed, age)
    @Override
    public void updatePet(Long petId, PetDTO petDTO) {
        Optional<Pet> petOpt = petRepository.findById(petId);

        if (petOpt.isPresent()) {
            Pet pet = petOpt.get();
            pet.setName(petDTO.getName());
            pet.setSpecies(petDTO.getSpecies());
            pet.setBreed(petDTO.getBreed());
            pet.setAge(petDTO.getAge());

            petRepository.save(pet);
        } else {
            throw new EntityNotFoundException("Pet not found with ID: " + petId);
        }
    }

    // Update prescriptions for a pet
    @Override
    public void updatePrescriptions(Long petId, List<Prescription> prescriptions) {
        Optional<Pet> petOpt = petRepository.findById(petId);
        if (petOpt.isPresent()) {
            Pet pet = petOpt.get();
            prescriptionRepository.deleteByPetId(petId); // Clear existing records

            for (Prescription prescription : prescriptions) {
                prescription.setPet(pet); // Set the Pet object for each prescription
                prescriptionRepository.save(prescription);
            }
        } else {
            throw new EntityNotFoundException("Pet not found with ID: " + petId);
        }
    }

    @Override
    public void updateTreatmentPlans(Long petId, List<TreatmentPlan> treatmentPlans) {
        Optional<Pet> petOpt = petRepository.findById(petId);
    
        if (petOpt.isPresent()) {
            Pet pet = petOpt.get();
    
            // Instead of clearing the existing records, we check for updates
            for (TreatmentPlan plan : treatmentPlans) {
                if (plan.getTreatmentPlanID() != null) {
                    // Update existing treatment plan
                    TreatmentPlan existingPlan = treatmentPlanRepository.findById(plan.getTreatmentPlanID())
                            .orElseThrow(() -> new EntityNotFoundException("Treatment Plan not found with ID: " + plan.getTreatmentPlanID()));
                    
                    existingPlan.setDiagnosis(plan.getDiagnosis());
                    existingPlan.setDateAdministered(plan.getDateAdministered());
                    existingPlan.setDescription(plan.getDescription());
                    existingPlan.setEndDate(plan.getEndDate());
                    existingPlan.setNotes(plan.getNotes());
    
                    treatmentPlanRepository.save(existingPlan);
                } else {
                    // Save new treatment plan if ID is null (for new plans)
                    plan.setPet(pet); // Set the Pet object for each treatment plan
                    treatmentPlanRepository.save(plan);
                }
            }
        } else {
            throw new EntityNotFoundException("Pet not found with ID: " + petId);
        }
    }
    

    // Update vaccination records for a pet
    @Override
    public void updateVaccinationRecords(Long petId, List<VaccinationRecord> vaccinations) {
        Optional<Pet> petOpt = petRepository.findById(petId);
        if (petOpt.isPresent()) {
            Pet pet = petOpt.get();
            vaccinationRecordRepository.deleteByPetId(petId); // Clear existing records

            for (VaccinationRecord vaccination : vaccinations) {
                vaccination.setPet(pet); // Set the Pet object for each vaccination
                vaccinationRecordRepository.save(vaccination);
            }
        } else {
            throw new EntityNotFoundException("Pet not found with ID: " + petId);
        }
    }

    // Update medical history for a pet
    @Override
    public void updateMedicalHistory(Long petId, MedicalHistory medicalHistory) {
        Optional<Pet> petOpt = petRepository.findById(petId);
        if (petOpt.isPresent()) {
            Pet pet = petOpt.get();
            Optional<MedicalHistory> existingHistory = medicalHistoryRepository.findByPet_petId(petId);

            if (existingHistory.isPresent()) {
                MedicalHistory history = existingHistory.get();
                history.setChronicConditions(medicalHistory.getChronicConditions());
                history.setAllergies(medicalHistory.getAllergies());
                history.setNotes(medicalHistory.getNotes());
                history.setLastVaccinationDate(medicalHistory.getLastVaccinationDate());
                history.setLastTreatmentDate(medicalHistory.getLastTreatmentDate());
                history.setLastPrescriptionDate(medicalHistory.getLastPrescriptionDate());
                history.setPet(pet);  // Set the Pet object

                medicalHistoryRepository.save(history);
            } else {
                medicalHistory.setPet(pet);  // Set the Pet object
                medicalHistoryRepository.save(medicalHistory);  // Save new history
            }
        } else {
            throw new EntityNotFoundException("Pet not found with ID: " + petId);
        }
    }

}