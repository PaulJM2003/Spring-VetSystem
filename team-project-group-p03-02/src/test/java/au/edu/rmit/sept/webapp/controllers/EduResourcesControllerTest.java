package au.edu.rmit.sept.webapp.controllers;

import au.edu.rmit.sept.webapp.models.EduResources;
import au.edu.rmit.sept.webapp.services.CustomUserDetailsService;
import au.edu.rmit.sept.webapp.services.EduResourcesService;
import au.edu.rmit.sept.webapp.services.SavedResourcesService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@WebMvcTest(EduResourcesController.class)
public class EduResourcesControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EduResourcesService eduService;

    @MockBean
    private CustomUserDetailsService userService;

    @MockBean
    private SavedResourcesService savedService;

    @Test
    @WithMockUser 
    void shouldListAllResources() throws Exception {
        // mock resources
        List<EduResources> videos = new ArrayList<>();
        List<EduResources> articles = new ArrayList<>();
        List<EduResources> guides = new ArrayList<>();

        // Mock the behavior of service
        when(eduService.findAllResourcesByType("Video")).thenReturn(videos);
        when(eduService.findAllResourcesByType("Article")).thenReturn(articles);
        when(eduService.findAllResourcesByType("Guide")).thenReturn(guides);

        // Perform request
        mockMvc.perform(get("/eduresources"))
                .andExpect(status().isOk())
                .andExpect(view().name("eduresources/index"));
    }
}
