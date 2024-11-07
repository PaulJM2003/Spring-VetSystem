package au.edu.rmit.sept.webapp.repositories;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import au.edu.rmit.sept.webapp.models.*;

import au.edu.rmit.sept.webapp.models.SavedResources;
import jakarta.transaction.Transactional;

import java.util.List;

@Repository
public interface SavedResourcesRepository extends JpaRepository<SavedResources, Long> {

    @Query("SELECT s FROM SavedResources s")
    List<SavedResources> findAllSavedResources();
    
    @Modifying
    @Transactional
    @Query("DELETE FROM SavedResources sr WHERE sr.user = :user AND sr.resources = :resources")
    void deleteSavedResource(@Param("user") CustomUser user, @Param("resources") EduResources resources);

}
