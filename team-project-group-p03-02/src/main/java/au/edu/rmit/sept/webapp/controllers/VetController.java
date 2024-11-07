package au.edu.rmit.sept.webapp.controllers;

import au.edu.rmit.sept.webapp.dto.PetDTO;
import au.edu.rmit.sept.webapp.models.*;
import au.edu.rmit.sept.webapp.services.PetService;
import au.edu.rmit.sept.webapp.services.UserService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/vet")
public class VetController {

    private final PetService petService;
    private final UserService userService;

    @Autowired
    public VetController(PetService petService, UserService userService) {
        this.petService = petService;
        this.userService = userService;
    }
    @GetMapping("/owners")
    public String listOwners(Model model) {
        List<User> owners = userService.getAllUsers();
        System.out.println("Owners List: " + owners); // Add this line to log the output
        if (owners.isEmpty()) {
            model.addAttribute("noOwnersMessage", "No pet owners found.");
        } else {
            model.addAttribute("owners", owners);
        }
        return "vet/ownerList";
    }


    @GetMapping("/owners/{userId}/pets")
public String listPets(@PathVariable Long userId, Model model) {
    // Fetch the owner based on the userId using getUserById
    User owner = userService.getUserById(userId)
            .orElseThrow(() -> new EntityNotFoundException("Owner not found with ID: " + userId));

    // Fetch the list of pets for this owner
    List<PetDTO> pets = petService.findPetsByUserId(userId);

    // Add the owner and pets to the model
    model.addAttribute("owner", owner); // Now the owner is properly defined and added
    if (pets.isEmpty()) {
        model.addAttribute("noPetsMessage", "No pets found for this owner.");
    } else {
        model.addAttribute("pets", pets); // Bind pets to the view
    }

    return "vet/petList"; // This should map to your Thymeleaf template for displaying pets
}
    // Edit pet form
// Edit pet form
@GetMapping("/owners/{userId}/pets/{petId}/edit")
public String editPet(@PathVariable Long userId, @PathVariable Long petId, Model model) {
    // Fetch the pet from the service layer
    Pet pet = petService.getPetById(petId);
    
    // Ensure that nested collections like vaccinations, treatmentPlans, and prescriptions are initialized
    if (pet.getVaccinations() == null || pet.getVaccinations().isEmpty()) {
        pet.setVaccinations(new ArrayList<>());
        pet.getVaccinations().add(new VaccinationRecord()); // Add at least one empty vaccination record for Thymeleaf rendering
    }
    
    if (pet.getTreatmentPlans() == null || pet.getTreatmentPlans().isEmpty()) {
        pet.setTreatmentPlans(new ArrayList<>());
        pet.getTreatmentPlans().add(new TreatmentPlan()); 
    }

    if (pet.getPrescriptions() == null || pet.getPrescriptions().isEmpty()) {
        pet.setPrescriptions(new ArrayList<>());
        pet.getPrescriptions().add(new Prescription()); // Add at least one empty prescription for Thymeleaf rendering
    }

    // Add the Pet object to the model for form binding
    model.addAttribute("pet", pet);

    // Add the userId to the model for URL construction and other purposes
    model.addAttribute("userId", userId);

    // Return the view for editing the pet
    return "vet/editPet";
}

@PostMapping("/owners/{userId}/pets/{petId}/save")
public String savePet(@PathVariable Long userId, @PathVariable Long petId, @ModelAttribute Pet petForm) {
    // Retrieve the existing pet from the database
    Pet existingPet = petService.findPetBypetId(petId)
            .orElseThrow(() -> new EntityNotFoundException("Pet not found with ID: " + petId));

    // Update the basic information of the pet (name, species, breed, age)
    existingPet.setName(petForm.getName());
    existingPet.setSpecies(petForm.getSpecies());
    existingPet.setBreed(petForm.getBreed());
    existingPet.setAge(petForm.getAge());

    // Update the pet's medical history
    MedicalHistory updatedMedicalHistory = petForm.getMedicalHistory();
    if (updatedMedicalHistory != null) {
        petService.updateMedicalHistory(petId, updatedMedicalHistory);
    }

    // Update the pet's vaccinations
    List<VaccinationRecord> updatedVaccinations = petForm.getVaccinations();
    if (updatedVaccinations != null && !updatedVaccinations.isEmpty()) {
        petService.updateVaccinationRecords(petId, updatedVaccinations);
    }

    // Update the pet's treatment plans
    List<TreatmentPlan> updatedTreatmentPlans = petForm.getTreatmentPlans();
    if (updatedTreatmentPlans != null && !updatedTreatmentPlans.isEmpty()) {
        petService.updateTreatmentPlans(petId, updatedTreatmentPlans);
    }

    // Update the pet's prescriptions
    List<Prescription> updatedPrescriptions = petForm.getPrescriptions();
    if (updatedPrescriptions != null && !updatedPrescriptions.isEmpty()) {
        for (Prescription prescription : updatedPrescriptions) {
            // Ensure that the medicine is persisted before saving the prescription
            Medicine medicine = prescription.getMedicine();
            if (medicine != null && medicine.getMedicineID() == null) {
                // Save the medicine if it's new (transient)
                petService.saveMedicine(medicine);
            }

            // Now save or update the prescription
            petService.updatePrescriptions(petId, updatedPrescriptions);
        }
    }

    // Save the pet with the updated details
    petService.savePet(existingPet);

    // Redirect back to the pet list for this user
    return "redirect:/vet/owners/" + userId + "/pets";
}
    // View vet's homepage
    @GetMapping("/home")
    public String vetHomePage() {
        return "vet/index"; // Renders 'vetHome.html'
    }
}
