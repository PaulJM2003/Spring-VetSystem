package au.edu.rmit.sept.webapp.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import au.edu.rmit.sept.webapp.models.CustomUser;
import au.edu.rmit.sept.webapp.models.EduResources;
import au.edu.rmit.sept.webapp.models.SavedResources;
import au.edu.rmit.sept.webapp.repositories.SavedResourcesRepository;
import java.util.List;


@Service
public class SavedResourcesServiceImpl implements SavedResourcesService{

    private final SavedResourcesRepository repo;
    
    @Autowired
    public SavedResourcesServiceImpl(SavedResourcesRepository repo) {
        this.repo = repo;
    }

    @Override
    public void saveResource(SavedResources resource) {
        repo.save(resource);
    }

    @Override
    public List<SavedResources> findAllSavedResources() {
        return repo.findAllSavedResources();
    }

    @Override
    public void deleteSavedResource(CustomUser user, EduResources resources) {
        repo.deleteSavedResource(user, resources);
    }

}
