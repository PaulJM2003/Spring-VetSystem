package au.edu.rmit.sept.webapp.models;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "appointment")
public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "appointment_id")
    private Long appointmentID;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "user_id")
    private CustomUser user;

    @ManyToOne
    @JoinColumn(name = "pet_id", referencedColumnName = "pet_id")
    private Pet pet;

    @ManyToOne
    @JoinColumn(name = "clinic_id", referencedColumnName = "clinic_id")
    private Clinic clinic;

    @Column(name = "appointment_date")
    private LocalDate appointmentDate;

    @Column(name = "status")
    private String status;

    @Column(name = "general_notes")
    private String generalNotes;

    @Column(name = "fees")
    private Float fees;

    @ManyToOne
    @JoinColumn(name = "vet_id", referencedColumnName = "user_id")
    private CustomUser vet;

    @Column(name = "appointment_time")
    private LocalTime appointmentTime;

    public Long getAppointmentID() {
        return appointmentID;
    }

    public void setAppointmentID(Long appointmentID) {
        this.appointmentID = appointmentID;
    }

    public CustomUser getUser() {
        return user;
    }

    public void setUser(CustomUser user) {
        this.user = user;
    }

    public Pet getPet() {
        return pet;
    }

    public void setPet(Pet pet) {
        this.pet = pet;
    }

    public Clinic getClinic() {
        return clinic;
    }

    public void setClinic(Clinic clinic) {
        this.clinic = clinic;
    }

    public LocalDate getAppointmentDate() {
        return appointmentDate;
    }

    public void setAppointmentDate(LocalDate appointmentDate) {
        this.appointmentDate = appointmentDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getGeneralNotes() {
        return generalNotes;
    }

    public void setGeneralNotes(String generalNotes) {
        this.generalNotes = generalNotes;
    }

    public Float getFees() {
        return fees;
    }

    public void setFees(Float fees) {
        this.fees = fees;
    }

    public CustomUser getVet() {
        return vet;
    }

    public void setVet(CustomUser vet) {
        this.vet = vet;
    }

    public LocalTime getAppointmentTime() {
        return appointmentTime;
    }

    public void setAppointmentTime(LocalTime appointmentTime) {
        this.appointmentTime = appointmentTime;
    }

    // Optional: Override toString() method
    @Override
    public String toString() {
        return "Appointment{" +
                "appointmentID=" + appointmentID +
                ", userID='" + user.getUserId() + '\'' +
                ", petID='" + pet.getPetId() + '\'' +
                ", clinicId='" + clinic.getClinicID() + '\'' +
                ", appointmentDate=" + appointmentDate +
                ", status='" + status + '\'' +
                ", generalNotes='" + generalNotes + '\'' +
                ", fees=" + fees +
                ", appointmentTime=" + appointmentTime +
                '}';
    }
}
