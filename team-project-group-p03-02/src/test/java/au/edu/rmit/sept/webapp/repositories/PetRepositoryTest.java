package au.edu.rmit.sept.webapp.repositories;

import au.edu.rmit.sept.webapp.models.Pet;
import au.edu.rmit.sept.webapp.models.CustomUser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
public class PetRepositoryTest {

    @Autowired
    private PetRepository petRepository;

    private CustomUser owner;

    @BeforeEach
    void setUp() {
        // Create a mock owner for the pets
        owner = new CustomUser();
        owner.setId(1L);
        
        // Create and save pets with the owner
        Pet pet1 = new Pet();
        pet1.setName("Max");
        pet1.setOwner(owner);
        pet1.setSpecies("Dog");
        pet1.setBreed("Golden Retriever");

        Pet pet2 = new Pet();
        pet2.setName("Bella");
        pet2.setOwner(owner);
        pet2.setSpecies("Cat");
        pet2.setBreed("Persian");

        petRepository.save(pet1);
        petRepository.save(pet2);
    }

    @Test
    void testFindByOwnerUserId_WhenPetsExist() {
        // Act: Fetch pets by owner ID
        List<Pet> pets = petRepository.findByOwnerUserId(1L);

        // Assert: Check that the results are as expected
        assertThat(pets).isNotNull();
        assertThat(pets.size()).isEqualTo(2);
        assertThat(pets.get(0).getName()).isEqualTo("Max");
        assertThat(pets.get(1).getName()).isEqualTo("Bella");
    }

    @Test
    void testFindByOwnerUserId_WhenNoPetsExist() {
        // Act: Fetch pets for an owner with no pets
        List<Pet> pets = petRepository.findByOwnerUserId(999L);

        // Assert: Check that no pets are returned
        assertThat(pets).isEmpty();
    }
}