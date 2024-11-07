package au.edu.rmit.sept.webapp.services;

import au.edu.rmit.sept.webapp.dto.DosageDTO;
import au.edu.rmit.sept.webapp.repositories.DosageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DosageService {

    @Autowired
    private DosageRepository dosageRepository;

    public List<DosageDTO> getDetailedDosageByPetIdAndMedicineId(Long petId, Long medicineId) {
        System.out.println("Fetching detailed dosages for petId: " + petId + " and medicineId: " + medicineId);
        return dosageRepository.findDetailedDosageByPetIdAndMedicineId(petId, medicineId);
    }
}
