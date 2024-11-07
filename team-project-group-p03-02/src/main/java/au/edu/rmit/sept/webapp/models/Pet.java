package au.edu.rmit.sept.webapp.models;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;

@Entity
@Table(name = "pet")
public class Pet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pet_id")
    private Long petId;

    @ManyToOne
    @JoinColumn(name = "owner_id", referencedColumnName = "user_id")
    private CustomUser owner;

    @Column(name = "name")
    private String name;

    @Column(name = "species")
    private String species;

    @Column(name = "breed")
    private String breed;

    @Column(name = "age")
    private Integer age;

      @OneToOne(mappedBy = "pet", cascade = CascadeType.ALL) // The pet field in MedicalHistory references this
     
      private MedicalHistory medicalHistory;

      

    @OneToMany(mappedBy = "pet", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<VaccinationRecord> vaccinations = new ArrayList<>();

    @OneToMany(mappedBy = "pet", cascade = CascadeType.ALL)
    private List<Prescription> prescriptions;

    @OneToMany(mappedBy = "pet", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TreatmentPlan> treatmentPlans = new ArrayList<>();

    public Pet() {
    }

    public Pet(Long petId, String name, String species, String breed, Integer age) {
        this.petId = petId;
        this.name = name;
        this.species = species;
        this.breed = breed;
        this.age = age;
    }

    // Getters and setters

    public Long getPetId() {
        return petId;
    }

    public void setPetId(Long petId) {
        this.petId = petId;
    }

    public CustomUser getOwner() {
        return owner;
    }

    public void setOwner(CustomUser owner) {
        this.owner = owner;
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

 // Getter and Setter for MedicalHistory
 public MedicalHistory getMedicalHistory() {
    return medicalHistory;
}

public void setMedicalHistory(MedicalHistory medicalHistory) {
    this.medicalHistory = medicalHistory;
}

// Getter and Setter for Vaccinations
public List<VaccinationRecord> getVaccinations() {
    return vaccinations;
}

public void setVaccinations(List<VaccinationRecord> vaccinations) {
    this.vaccinations = vaccinations;
}

// Getter and Setter for TreatmentPlans
public List<TreatmentPlan> getTreatmentPlans() {
    return treatmentPlans;
}

public void setTreatmentPlans(List<TreatmentPlan> treatmentPlans) {
    this.treatmentPlans = treatmentPlans;
}

// Getter and Setter for Prescriptions
public List<Prescription> getPrescriptions() {
    return prescriptions;
}

public void setPrescriptions(List<Prescription> prescriptions) {
    this.prescriptions = prescriptions;
}
    @Override
    public String toString() {
        return "Pet{" +
                "petId=" + petId +
                ", owner=" + owner +
                ", name='" + name + '\'' +
                ", species='" + species + '\'' +
                ", breed='" + breed + '\'' +
                ", age=" + age +
                '}';
    }
}
