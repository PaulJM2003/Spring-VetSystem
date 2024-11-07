package au.edu.rmit.sept.webapp.controllers;

import au.edu.rmit.sept.webapp.dto.PetDTO;
import au.edu.rmit.sept.webapp.models.CustomUser;
import au.edu.rmit.sept.webapp.models.Pet;
import au.edu.rmit.sept.webapp.models.Prescription;
import au.edu.rmit.sept.webapp.models.TreatmentPlan;
import au.edu.rmit.sept.webapp.models.VaccinationRecord;
import au.edu.rmit.sept.webapp.services.CustomUserDetailsService;
import au.edu.rmit.sept.webapp.services.PetMedicalHistoryService;
import au.edu.rmit.sept.webapp.services.PetService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

public class PetControllerTest {

    private MockMvc mockMvc;

    @Mock
    private PetService petService;

    @Mock
    private CustomUserDetailsService customUserDetailsService;

    @Mock
    private PetMedicalHistoryService petMedicalHistoryService;

    @InjectMocks
    private PetController petController;

    @Mock
    private Model model;

    private CustomUser currentUser;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(petController).build();
        currentUser = new CustomUser();
        currentUser.setId(1L);
    }

    // Test to check if the view for registered pets is returned correctly with pets data
    @Test
    void viewRegisteredPets_WithPets() throws Exception {
        // Arrange: mock a list of pets
        List<PetDTO> pets = new ArrayList<>();
        Pet pet = new Pet(1L, "Buddy", "Dog", "Labrador", 3);
        pets.add(new PetDTO(pet));

        when(customUserDetailsService.getCurrentUser()).thenReturn(currentUser);
        when(petService.findPetsByUserId(currentUser.getUserId())).thenReturn(pets);

        // Act and Assert: Perform the get request and validate response
        mockMvc.perform(get("/pets"))
                .andExpect(status().isOk())
                .andExpect(view().name("pets/index"))
                .andExpect(model().attributeExists("pets"))
                .andExpect(model().attribute("pets", pets));
    }

    // Test to check if the view for registered pets shows no pets message when empty
    @Test
    void viewRegisteredPets_NoPets() throws Exception {
        // Arrange: mock an empty list of pets
        List<PetDTO> emptyPets = new ArrayList<>();

        when(customUserDetailsService.getCurrentUser()).thenReturn(currentUser);
        when(petService.findPetsByUserId(currentUser.getUserId())).thenReturn(emptyPets);

        // Act and Assert: Perform the get request and validate response
        mockMvc.perform(get("/pets"))
                .andExpect(status().isOk())
                .andExpect(view().name("pets/index"))
                .andExpect(model().attributeExists("noPetsMessage"))
                .andExpect(model().attribute("noPetsMessage", "You have no registered pets."));
    }

    // Test for showing pet registration form
    @Test
    void showPetRegistrationForm() throws Exception {
        mockMvc.perform(get("/pets/new"))
                .andExpect(status().isOk())
                .andExpect(view().name("pets/new"));
    }

    // Test to check if the view for a specific pet’s medical history is shown
    @Test
    void viewPetMedicalHistory_PetExists() throws Exception {
        // Arrange: mock a pet and its medical history data
        Pet pet = new Pet();
        pet.setPetId(1L);

        List<VaccinationRecord> vaccinations = new ArrayList<>();
        List<TreatmentPlan> treatmentPlans = new ArrayList<>();
        List<Prescription> prescriptions = new ArrayList<>();

        when(petService.findPetBypetId(1L)).thenReturn(java.util.Optional.of(pet));
        when(petMedicalHistoryService.getVaccinationRecordsBypetId(1L)).thenReturn(vaccinations);
        when(petMedicalHistoryService.getTreatmentPlansBypetId(1L)).thenReturn(treatmentPlans);
        when(petMedicalHistoryService.getPrescriptionsBypetId(1L)).thenReturn(prescriptions);

        // Act and Assert: Perform the get request and validate response
        mockMvc.perform(get("/pets/1/view"))
                .andExpect(status().isOk())
                .andExpect(view().name("pets/view"))
                .andExpect(model().attributeExists("pet"))
                .andExpect(model().attributeExists("vaccinations"))
                .andExpect(model().attributeExists("treatmentPlans"))
                .andExpect(model().attributeExists("prescriptions"));
    }

    // Test to check if the error message is shown when the pet does not exist
    @Test
    void viewPetMedicalHistory_PetDoesNotExist() throws Exception {
        when(petService.findPetBypetId(1L)).thenReturn(java.util.Optional.empty());

        mockMvc.perform(get("/pets/1/view"))
                .andExpect(status().isOk())
                .andExpect(view().name("pets/error"))
                .andExpect(model().attributeExists("errorMessage"))
                .andExpect(model().attribute("errorMessage", "Pet not found."));
    }
}