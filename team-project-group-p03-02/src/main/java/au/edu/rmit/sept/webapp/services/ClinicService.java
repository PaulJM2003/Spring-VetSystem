package au.edu.rmit.sept.webapp.services;
import au.edu.rmit.sept.webapp.models.Clinic;

import java.util.List;


public interface ClinicService {
    public List<Clinic> getClinics();
    public Clinic findClinicByName(String name);
}
