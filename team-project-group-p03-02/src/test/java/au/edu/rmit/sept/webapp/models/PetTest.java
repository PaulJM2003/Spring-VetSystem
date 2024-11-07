package au.edu.rmit.sept.webapp.models;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class PetTest {

    @Test
    public void testPetGettersAndSetters() {
        Pet pet = new Pet();

        pet.setPetId(1L);
        CustomUser owner = new CustomUser();
        pet.setOwner(owner);
        pet.setName("Buddy");
        pet.setSpecies("Dog");
        pet.setBreed("Golden Retriever");
        pet.setAge(5);

        assertEquals(1L, pet.getPetId());
        assertEquals(owner, pet.getOwner());
        assertEquals("Buddy", pet.getName());
        assertEquals("Dog", pet.getSpecies());
        assertEquals("Golden Retriever", pet.getBreed());
        assertEquals(5, pet.getAge());
    }
}
