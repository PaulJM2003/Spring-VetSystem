package au.edu.rmit.sept.webapp.services;

import au.edu.rmit.sept.webapp.models.CustomUser;
import au.edu.rmit.sept.webapp.models.EduResources;
import au.edu.rmit.sept.webapp.models.SavedResources;
import au.edu.rmit.sept.webapp.repositories.SavedResourcesRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;

public class SavedResourcesServiceImplTest {

    @InjectMocks
    private SavedResourcesServiceImpl savedResourcesService;

    @Mock
    private SavedResourcesRepository savedResourcesRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testSaveResource() {

        EduResources eduResources = new EduResources();
        SavedResources savedResource = new SavedResources();
        eduResources.setResourceID(1L);
        savedResource.setResources(eduResources);

        savedResourcesService.saveResource(savedResource);

        verify(savedResourcesRepository, times(1)).save(savedResource);
    }

    @Test
    public void testDeleteSavedResource() {

        CustomUser user = new CustomUser();
        EduResources resource = new EduResources();

        savedResourcesService.deleteSavedResource(user, resource);

        verify(savedResourcesRepository, times(1)).deleteSavedResource(user, resource);
    }
}
