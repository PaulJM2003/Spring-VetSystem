package au.edu.rmit.sept.webapp.repositories;

import au.edu.rmit.sept.webapp.models.VaccinationRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface VaccinationRecordRepository extends JpaRepository<VaccinationRecord, Long> {

    List<VaccinationRecord> findByPet_petId(Long petId);
    @Query("SELECT v.dateAdministered FROM VaccinationRecord v WHERE v.pet.petId = :petId ORDER BY v.dateAdministered DESC")
    Optional<LocalDate> findLatestVaccinationDateByPetId(@Param("petId") Long petId);
   
    @Modifying
    @Transactional
    @Query("DELETE FROM VaccinationRecord v WHERE v.pet.petId = :petId")
    void deleteByPetId(Long petId);
}

