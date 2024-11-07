package au.edu.rmit.sept.webapp.services;

import au.edu.rmit.sept.webapp.models.*;
import au.edu.rmit.sept.webapp.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class PetMedicalHistoryService {

    private final MedicalHistoryRepository medicalHistoryRepository;
    private final VaccinationRecordRepository vaccinationRecordRepository;
    private final TreatmentPlanRepository treatmentPlanRepository;
    private final PrescriptionRepository prescriptionRepository;

  

    @Autowired
    public PetMedicalHistoryService(MedicalHistoryRepository medicalHistoryRepository,
                                    VaccinationRecordRepository vaccinationRecordRepository,
                                    TreatmentPlanRepository treatmentPlanRepository,
                                    PrescriptionRepository prescriptionRepository) {
        this.medicalHistoryRepository = medicalHistoryRepository;
        this.vaccinationRecordRepository = vaccinationRecordRepository;
        this.treatmentPlanRepository = treatmentPlanRepository;
        this.prescriptionRepository = prescriptionRepository;
    }

    public Optional<MedicalHistory> getMedicalHistoryBypetId(Long petId) {
        return medicalHistoryRepository.findByPet_petId(petId);
    }

    public List<VaccinationRecord> getVaccinationRecordsBypetId(Long petId) {
        return vaccinationRecordRepository.findByPet_petId(petId);
    }

    public List<TreatmentPlan> getTreatmentPlansBypetId(Long petId) {
        return treatmentPlanRepository.findByPet_petId(petId);
    }

    public List<Prescription> getPrescriptionsBypetId(Long petId) {
        return prescriptionRepository.findByPet_petId(petId);
    }
   
    
    
  
    
    
}
