package au.edu.rmit.sept.webapp.services;

import au.edu.rmit.sept.webapp.models.EduResources;
import au.edu.rmit.sept.webapp.repositories.EduResourcesRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class EduResourcesServiceImplTest {

    @InjectMocks
    private EduResourcesServiceImpl eduResourcesService;

    @Mock
    private EduResourcesRepository eduResourcesRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testFindAllResourcesByCategory() {
        
        EduResources videoResource = new EduResources();
        videoResource.setResourceID(1L);
        videoResource.setCategory("Video");

        EduResources articleResource = new EduResources();
        articleResource.setResourceID(2L);
        articleResource.setCategory("Article");

        EduResources guideResource = new EduResources();
        articleResource.setResourceID(2L);
        articleResource.setCategory("Guide");

        when(eduResourcesRepository.findAllResourcesByType("Video")).thenReturn(Arrays.asList(videoResource));
        when(eduResourcesRepository.findAllResourcesByType("Article")).thenReturn(Arrays.asList(articleResource));
        when(eduResourcesRepository.findAllResourcesByType("Guide")).thenReturn(Arrays.asList(guideResource));

        List<EduResources> videos = eduResourcesService.findAllResourcesByType("Video");
        List<EduResources> articles = eduResourcesService.findAllResourcesByType("Article");
        List<EduResources> guide = eduResourcesService.findAllResourcesByType("Guide");

        assertEquals(1, videos.size());
        assertEquals(videoResource, videos.get(0));
        assertEquals(1, articles.size());
        assertEquals(articleResource, articles.get(0));
        assertEquals(1, guide.size());
        assertEquals(guideResource, guide.get(0));
    }

    @Test
    public void testFindResourceByID() {

        EduResources resource = new EduResources();
        resource.setResourceID(1L);
        
        when(eduResourcesRepository.findById(1L)).thenReturn(Optional.of(resource));

        EduResources foundResource = eduResourcesService.findResourceById(1L);
        
        assertNotNull(foundResource);
        assertEquals(resource, foundResource);
    }
}
