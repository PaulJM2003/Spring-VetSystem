package au.edu.rmit.sept.webapp.repositories;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.data.repository.query.Param;
import au.edu.rmit.sept.webapp.models.Clinic;

@Repository
public interface ClinicRepository extends JpaRepository<Clinic, Long> {

    @Query("SELECT c.clinicId FROM Clinic c WHERE c.name = :name")
    Long findClinicIDByName(@Param("name") String name);
}