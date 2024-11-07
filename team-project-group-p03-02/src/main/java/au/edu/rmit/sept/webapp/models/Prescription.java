package au.edu.rmit.sept.webapp.models;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "prescription")
public class Prescription {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "prescription_id")
    private Long prescriptionID;

    @ManyToOne
    @JoinColumn(name = "pet_id", referencedColumnName = "pet_id")
    private Pet pet;

    @ManyToOne
    @JoinColumn(name = "medicine_id", referencedColumnName = "medicine_id")
    private Medicine medicine;

    @Column(name = "instructions")
    private String instructions;

    @Column(name = "dosage_quantity")
    private String dosageQuantity;

    @Column(name = "date_administered")
    private LocalDate dateAdministered;

    @Column(name = "expiry_date")
    private LocalDate expiryDate;

    @Column(name = "repeats_left")
    private Integer repeatsLeft;

    @Column(name = "renewal_date")
    private LocalDate renewalDate;

    // Getters and Setters
    public Long getPrescriptionID() {
        return prescriptionID;
    }

    public void setPrescriptionID(Long prescriptionID) {
        this.prescriptionID = prescriptionID;
    }

    public Pet getPet() {
        return pet;
    }

    public void setPet(Pet pet) {
        this.pet = pet;
    }

    public Medicine getMedicine() {
        return medicine;
    }

    public void setMedicine(Medicine medicine) {
        this.medicine = medicine;
    }

    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }

    public String getDosageQuantity() {
        return dosageQuantity;
    }

    public void setDosageQuantity(String dosageQuantity) {
        this.dosageQuantity = dosageQuantity;
    }

    public LocalDate getDateAdministered() {
        return dateAdministered;
    }

    public void setDateAdministered(LocalDate dateAdministered) {
        this.dateAdministered = dateAdministered;
    }

    public LocalDate getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(LocalDate expiryDate) {
        this.expiryDate = expiryDate;
    }

    public Integer getRepeatsLeft() {
        return repeatsLeft;
    }

    public void setRepeatsLeft(Integer repeatsLeft) {
        this.repeatsLeft = repeatsLeft;
    }

    public LocalDate getRenewalDate() {
        return renewalDate;
    }

    public void setRenewalDate(LocalDate renewalDate) {
        this.renewalDate = renewalDate;
    }
}
