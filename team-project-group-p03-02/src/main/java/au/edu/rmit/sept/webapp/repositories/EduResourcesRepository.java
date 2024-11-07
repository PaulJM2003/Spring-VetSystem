package au.edu.rmit.sept.webapp.repositories;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

import au.edu.rmit.sept.webapp.models.EduResources;

@Repository
public interface EduResourcesRepository extends JpaRepository<EduResources, Long> {

    @Query("SELECT e FROM EduResources e WHERE e.resourceType = :resourceType")
    List<EduResources> findAllResourcesByType(@Param("resourceType") String resourceType);

    @Query("SELECT e FROM EduResources e WHERE LOWER(e.description) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
           "OR LOWER(e.title) LIKE LOWER(CONCAT('%', :keyword, '%'))" +
           "OR LOWER(e.category) LIKE LOWER(CONCAT('%', :keyword, '%'))" +
           "OR LOWER(e.author) LIKE LOWER(CONCAT('%', :keyword, '%'))" +
           "OR LOWER(e.resourceType) LIKE LOWER(CONCAT('%', :keyword, '%'))" +
           "OR LOWER(FUNCTION('DATE_FORMAT', e.publishDate, '%Y-%m-%d')) LIKE LOWER(CONCAT('%', :keyword, '%'))" +
           "OR LOWER(e.content) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<EduResources> findResourceBySearch(@Param("keyword") String keyword);
}
