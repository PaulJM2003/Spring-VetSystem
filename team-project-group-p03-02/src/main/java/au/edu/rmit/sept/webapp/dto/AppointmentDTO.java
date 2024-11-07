package au.edu.rmit.sept.webapp.dto;

import java.time.LocalDate;
import java.time.LocalTime;

public class AppointmentDTO {
        private Long appointmentID;
        private LocalDate appointmentDate;
        private LocalTime appointmentTime;
        private Long petId;
        private String generalNotes;
        private String status;
        private Float fees;

    public AppointmentDTO() {
        
    }

    public AppointmentDTO(Long appointmentID, LocalDate appointmentDate, LocalTime appointmentTime, Long petId, String generalNotes, String status,Float fees) {
        this.appointmentID = appointmentID;
        this.appointmentDate = appointmentDate;
        this.appointmentTime = appointmentTime;
        this.petId = petId;
        this.generalNotes = generalNotes;
        this.status = status;
        this.fees = fees;
    }

    // Getters and Setters
    public Long getAppointmentID() {
        return appointmentID;
    }

    public void setAppointmentID(Long appointmentID) {
        this.appointmentID = appointmentID;
    }

    public LocalDate getAppointmentDate() {
        return appointmentDate;
    }

    public void setAppointmentDate(LocalDate appointmentDate) {
        this.appointmentDate = appointmentDate;
    }

    public LocalTime getAppointmentTime() {
        return appointmentTime;
    }

    public void setAppointmentTime(LocalTime appointmentTime) {
        this.appointmentTime = appointmentTime;
    }

    public Long getPetId() {
        return petId;
    }

    // Setter
    public void setPetId(Long petId) {
        this.petId = petId;
    }

    public String getGeneralNotes() {
        return generalNotes;
    }

    public void setGeneralNotes(String generalNotes) {
        this.generalNotes = generalNotes;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Float getFees() {
        return fees;
    }

    public void setFees(Float fees) {
        this.fees = fees;
    }
}