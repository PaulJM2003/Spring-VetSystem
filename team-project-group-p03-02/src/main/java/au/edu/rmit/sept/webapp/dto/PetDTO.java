package au.edu.rmit.sept.webapp.dto;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import au.edu.rmit.sept.webapp.models.*;

public class PetDTO {

    private Long petId;
    private String name;
    private String species;
    private String breed;
    private Integer age;

    private LocalDate lastVaccinationDate;
    private boolean activePrescriptions;
    private boolean activeTreatments;
    private MedicalHistory medicalHistory; // Field for medical history

  // Add these fields
    private List<VaccinationRecord> vaccinations = new ArrayList<>();
    private List<TreatmentPlan> treatmentPlans = new ArrayList<>();
    private List<Prescription> prescriptions = new ArrayList<>();

    // Constructor
    public PetDTO(Pet pet) {
        this.petId = pet.getPetId();
        this.name = pet.getName();
        this.species = pet.getSpecies();
        this.breed = pet.getBreed();
        this.age = pet.getAge();
        this.medicalHistory = pet.getMedicalHistory(); // Ensure this is being set from the Pet model
        
    }

    // Getters and Setters

    public Long getPetId() {
        return petId;
    }

    public void setPetId(Long petId) {
        this.petId = petId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSpecies() {
        return species;
    }

    public void setSpecies(String species) {
        this.species = species;
    }

    public String getBreed() {
        return breed;
    }

    public void setBreed(String breed) {
        this.breed = breed;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public LocalDate getLastVaccinationDate() {
        return lastVaccinationDate;
    }

    public void setLastVaccinationDate(LocalDate lastVaccinationDate) {
        this.lastVaccinationDate = lastVaccinationDate;
    }

    public boolean isActivePrescriptions() {
        return activePrescriptions;
    }

    public void setActivePrescriptions(boolean activePrescriptions) {
        this.activePrescriptions = activePrescriptions;
    }

    public boolean isActiveTreatments() {
        return activeTreatments;
    }

    public void setActiveTreatments(boolean activeTreatments) {
        this.activeTreatments = activeTreatments;
    }
   
    public MedicalHistory getMedicalHistory() {
        return medicalHistory;
    }

    public void setMedicalHistory(MedicalHistory medicalHistory) {
        this.medicalHistory = medicalHistory;
    }
    public List<VaccinationRecord> getVaccinations() {
        if (vaccinations == null) {
            vaccinations = new ArrayList<>();
        }
        return vaccinations;
    }

    public void setVaccinations(List<VaccinationRecord> vaccinations) {
        this.vaccinations = vaccinations;
    }

    public List<TreatmentPlan> getTreatmentPlans() {
        if (treatmentPlans == null) {
            treatmentPlans = new ArrayList<>();
        }
        return treatmentPlans;
    }

    public void setTreatmentPlans(List<TreatmentPlan> treatmentPlans) {
        this.treatmentPlans = treatmentPlans;
    }

    public List<Prescription> getPrescriptions() {
        if (prescriptions == null) {
            prescriptions = new ArrayList<>();
        }
        return prescriptions;
    }

    public void setPrescriptions(List<Prescription> prescriptions) {
        this.prescriptions = prescriptions;
    }
}

