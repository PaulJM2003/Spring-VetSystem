package au.edu.rmit.sept.webapp.repositories;

import au.edu.rmit.sept.webapp.dto.DosageDTO;
import au.edu.rmit.sept.webapp.models.Dosage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DosageRepository extends JpaRepository<Dosage, Long> {

    @Query("SELECT new au.edu.rmit.sept.webapp.dto.DosageDTO(m.name, d.dosageQuantity, d.instructions, d.dateAdministered, d.nextDosageDate, m.sideEffects) " +
        "FROM Dosage d " +
        "JOIN d.medicine m " +
        "WHERE d.pet.petId = :petId AND d.medicine.medicineID = :medicineId")
    List<DosageDTO> findDetailedDosageByPetIdAndMedicineId(@Param("petId") Long petId, @Param("medicineId") Long medicineId);

}
