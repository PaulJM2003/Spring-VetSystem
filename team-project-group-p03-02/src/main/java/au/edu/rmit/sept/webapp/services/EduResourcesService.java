package au.edu.rmit.sept.webapp.services;

import au.edu.rmit.sept.webapp.models.EduResources;

import java.util.List;

public interface EduResourcesService {
    public List<EduResources> findAllResourcesByType(String resourceType);
    public EduResources findResourceById(Long id);
    public List<EduResources> findResourceBySearch(String keyword);
}
