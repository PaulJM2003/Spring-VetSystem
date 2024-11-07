package au.edu.rmit.sept.webapp.models;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "dosage")
public class Dosage {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long dosageId;
    
    @ManyToOne
    @JoinColumn(name = "pet_id")
    private Pet pet;

    @ManyToOne
    @JoinColumn(name = "medicine_id")
    private Medicine medicine;
    
    private String dosageQuantity;
    private String instructions;
    private Date dateAdministered;
    private Date nextDosageDate;

    public Long getDosageId() {
        return dosageId;
    }

    public void setDosageId(Long dosageId) {
        this.dosageId = dosageId;
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

    public String getDosageQuantity() {
        return dosageQuantity;
    }

    public void setDosageQuantity(String dosageQuantity) {
        this.dosageQuantity = dosageQuantity;
    }

    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }

    public Date getDateAdministered() {
        return dateAdministered;
    }

    public void setDateAdministered(Date dateAdministered) {
        this.dateAdministered = dateAdministered;
    }

    public Date getNextDosageDate() {
        return nextDosageDate;
    }

    public void setNextDosageDate(Date nextDosageDate) {
        this.nextDosageDate = nextDosageDate;
    }
}
