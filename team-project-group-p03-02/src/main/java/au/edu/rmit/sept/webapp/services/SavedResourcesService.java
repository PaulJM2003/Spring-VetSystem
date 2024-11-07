package au.edu.rmit.sept.webapp.services;

import au.edu.rmit.sept.webapp.models.CustomUser;
import au.edu.rmit.sept.webapp.models.EduResources;
import au.edu.rmit.sept.webapp.models.SavedResources;
import java.util.List;


public interface SavedResourcesService {
    public void saveResource(SavedResources resource);
    public List<SavedResources> findAllSavedResources();
    public void deleteSavedResource(CustomUser user, EduResources resources);
}
