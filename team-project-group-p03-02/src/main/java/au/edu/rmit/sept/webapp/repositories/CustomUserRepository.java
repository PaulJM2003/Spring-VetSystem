package au.edu.rmit.sept.webapp.repositories;

import au.edu.rmit.sept.webapp.models.CustomUser;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.Query;
import au.edu.rmit.sept.webapp.enums.UserType;

import java.util.List;


public interface CustomUserRepository extends JpaRepository<CustomUser, Long> {
    CustomUser findByName(String username);
    CustomUser findByEmail(String email);

    @Query("SELECT u.userId FROM CustomUser u WHERE u.userType = :userType")
    List<Long> findVetID(@Param("userType") UserType userType);

    @Query("SELECT u.userId FROM CustomUser u WHERE u.name = :name")
    Long findVetIDByName(@Param("name") String name);
}
