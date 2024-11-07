package au.edu.rmit.sept.webapp.services;

import org.openqa.selenium.NoSuchElementException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import au.edu.rmit.sept.webapp.models.EduResources;
import au.edu.rmit.sept.webapp.repositories.EduResourcesRepository;

import java.util.List;

@Service
public class EduResourcesServiceImpl implements EduResourcesService{

    private final EduResourcesRepository repo;
    
    @Autowired
    public EduResourcesServiceImpl(EduResourcesRepository repo) {
        this.repo = repo;
    }

    @Override
    public List<EduResources> findAllResourcesByType(String resourceType) {
        return repo.findAllResourcesByType(resourceType);
    }

    @Override
    public EduResources findResourceById(Long id) {
        return repo.findById(id)
        .orElseThrow(() -> new NoSuchElementException("Resource not found with id " + id));
    }
    
    @Override
    public List<EduResources> findResourceBySearch(String keyword) {
        return repo.findResourceBySearch(keyword);
    }
}
