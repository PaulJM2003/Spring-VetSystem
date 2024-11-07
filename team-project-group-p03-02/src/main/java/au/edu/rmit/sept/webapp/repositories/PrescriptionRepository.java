package au.edu.rmit.sept.webapp.repositories;

import au.edu.rmit.sept.webapp.models.Prescription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface PrescriptionRepository extends JpaRepository<Prescription, Long> {

    List<Prescription> findByPet_petId(Long petId);
    boolean existsByPet_PetIdAndExpiryDateAfter(Long petId, LocalDate date);
    Prescription findByPrescriptionID(Long id);
   
    @Modifying
    @Transactional
    @Query("DELETE FROM Prescription p WHERE p.pet.petId = :petId")
    void deleteByPetId(Long petId);
}
