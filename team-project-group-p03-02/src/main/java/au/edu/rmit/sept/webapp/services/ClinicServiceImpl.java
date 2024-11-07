package au.edu.rmit.sept.webapp.services;

import au.edu.rmit.sept.webapp.repositories.ClinicRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import au.edu.rmit.sept.webapp.models.Clinic;
import java.util.NoSuchElementException;

@Service
public class ClinicServiceImpl implements ClinicService {

    private final ClinicRepository repository;

    @Autowired
    public ClinicServiceImpl(ClinicRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Clinic> getClinics() {
        return repository.findAll();
    }

    @Override
    public Clinic findClinicByName(String name) {
        Long clinicId = repository.findClinicIDByName(name);

        return repository.findById(clinicId)
        .orElseThrow(() -> new NoSuchElementException("Clinic not found with id " + clinicId));
    }   
}