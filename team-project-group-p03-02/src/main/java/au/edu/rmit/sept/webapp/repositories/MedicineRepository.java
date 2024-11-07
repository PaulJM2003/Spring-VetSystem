package au.edu.rmit.sept.webapp.repositories;

import au.edu.rmit.sept.webapp.models.Medicine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MedicineRepository extends JpaRepository<Medicine, Long> {
    // You can add custom query methods here if needed
}