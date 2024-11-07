package au.edu.rmit.sept.webapp.repositories;

import au.edu.rmit.sept.webapp.models.Pet;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PetRepository extends JpaRepository<Pet, Long> {

    List<Pet> findByOwnerUserId(Long userId);
 @EntityGraph(attributePaths = {"vaccinations", "treatmentPlans", "prescriptions"})
    Optional<Pet> findById(Long petId);
}
