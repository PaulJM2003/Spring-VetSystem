package au.edu.rmit.sept.webapp.dto;

import java.util.Date;

public class DosageDTO {
    private String medicineName;
    private String dosageQuantity;
    private String instructions;
    private Date dateAdministered;
    private Date nextDosageDate;
    private String sideEffects;

    public DosageDTO(String medicineName, String dosageQuantity, String instructions, Date dateAdministered, Date nextDosageDate, String sideEffects) {
        this.medicineName = medicineName;
        this.dosageQuantity = dosageQuantity;
        this.instructions = instructions;
        this.dateAdministered = dateAdministered;
        this.nextDosageDate = nextDosageDate;
        this.sideEffects = sideEffects;
    }

    public String getMedicineName() { return medicineName; }
    public void setMedicineName(String medicineName) { this.medicineName = medicineName; }

    public String getDosageQuantity() { return dosageQuantity; }
    public void setDosageQuantity(String dosageQuantity) { this.dosageQuantity = dosageQuantity; }

    public String getInstructions() { return instructions; }
    public void setInstructions(String instructions) { this.instructions = instructions; }

    public Date getDateAdministered() { return dateAdministered; }
    public void setDateAdministered(Date dateAdministered) { this.dateAdministered = dateAdministered; }

    public Date getNextDosageDate() { return nextDosageDate; }
    public void setNextDosageDate(Date nextDosageDate) { this.nextDosageDate = nextDosageDate; }

    public String getSideEffects() { return sideEffects; }
    public void setSideEffects(String sideEffects) { this.sideEffects = sideEffects; }
}
