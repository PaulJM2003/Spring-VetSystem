package au.edu.rmit.sept.webapp.models;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "medical_history")
public class MedicalHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "medical_history_id")
    private Long medicalHistoryID;

    @OneToOne
    @JoinColumn(name = "pet_id", referencedColumnName = "pet_id")
    private Pet pet;

    @Column(name = "chronic_conditions")
    private String chronicConditions;

    @Column(name = "allergies")
    private String allergies;

    @Column(name = "notes")
    private String notes;

    @Column(name = "last_vaccination_date")
    private LocalDate lastVaccinationDate;

    @Column(name = "last_treatment_date")
    private LocalDate lastTreatmentDate;

    @Column(name = "last_prescription_date")
    private LocalDate lastPrescriptionDate;

    // Getters and Setters
    public Long getMedicalHistoryID() {
        return medicalHistoryID;
    }

    public void setMedicalHistoryID(Long medicalHistoryID) {
        this.medicalHistoryID = medicalHistoryID;
    }

    public Pet getPet() {
        return pet;
    }

    public void setPet(Pet pet) {
        this.pet = pet;
    }

    public String getChronicConditions() {
        return chronicConditions;
    }

    public void setChronicConditions(String chronicConditions) {
        this.chronicConditions = chronicConditions;
    }

    public String getAllergies() {
        return allergies;
    }

    public void setAllergies(String allergies) {
        this.allergies = allergies;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public LocalDate getLastVaccinationDate() {
        return lastVaccinationDate;
    }

    public void setLastVaccinationDate(LocalDate lastVaccinationDate) {
        this.lastVaccinationDate = lastVaccinationDate;
    }

    public LocalDate getLastTreatmentDate() {
        return lastTreatmentDate;
    }

    public void setLastTreatmentDate(LocalDate lastTreatmentDate) {
        this.lastTreatmentDate = lastTreatmentDate;
    }

    public LocalDate getLastPrescriptionDate() {
        return lastPrescriptionDate;
    }

    public void setLastPrescriptionDate(LocalDate lastPrescriptionDate) {
        this.lastPrescriptionDate = lastPrescriptionDate;
    }
}
