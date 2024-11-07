package au.edu.rmit.sept.webapp.repositories;

import au.edu.rmit.sept.webapp.models.TreatmentPlan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface TreatmentPlanRepository extends JpaRepository<TreatmentPlan, Long> {

    List<TreatmentPlan> findByPet_petId(Long petId);
    boolean existsByPet_PetIdAndEndDateAfter(Long petId, LocalDate date);
    @Modifying
    @Transactional
    @Query("DELETE FROM TreatmentPlan t WHERE t.pet.petId = :petId")
    void deleteByPetId(Long petId);

}
