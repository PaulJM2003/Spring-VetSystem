package au.edu.rmit.sept.webapp.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import au.edu.rmit.sept.webapp.services.CustomUserDetailsService;
import au.edu.rmit.sept.webapp.services.EduResourcesService;
import au.edu.rmit.sept.webapp.services.SavedResourcesService;
import au.edu.rmit.sept.webapp.models.EduResources;
import au.edu.rmit.sept.webapp.models.SavedResources;
import au.edu.rmit.sept.webapp.models.CustomUser;

import java.time.LocalDateTime;

import java.util.List;

@Controller
@RequestMapping("/eduresources") 
public class EduResourcesController {

    private final EduResourcesService eduService;
    private final CustomUserDetailsService userService;
    private final SavedResourcesService savedService;

    @Autowired
    public EduResourcesController(EduResourcesService eduService, CustomUserDetailsService userService, SavedResourcesService savedService) {
        this.eduService = eduService;
        this.userService = userService;
        this.savedService = savedService;
    }

    @GetMapping
    public String viewAllResources(Model model) {
        //search result
        String searchResult = "";
        model.addAttribute("searchResult", searchResult);

        //find all videos, articles and guides
        List<EduResources> videos = eduService.findAllResourcesByType("Video");
        List<EduResources> articles = eduService.findAllResourcesByType("Article");
        List<EduResources> guides = eduService.findAllResourcesByType("Guide");
        model.addAttribute("videos", videos);
        model.addAttribute("articles", articles);
        model.addAttribute("guides", guides);

        return "eduresources/index";
    }

    @PostMapping("/save_resource")
    public String saveResource(@RequestParam("resourceID") Long resourceID, RedirectAttributes redirectAttributes) {

        //get resource, date and time, user
        SavedResources savedResource = new SavedResources();
        LocalDateTime currentDateTime = LocalDateTime.now();
        CustomUser currentUser = userService.getCurrentUser();
        EduResources resource = eduService.findResourceById(resourceID);

        //save resources
        savedResource.setUser(currentUser);
        savedResource.setSavedAt(currentDateTime);
        savedResource.setResources(resource);
        savedService.saveResource(savedResource);

        redirectAttributes.addFlashAttribute("message", "Resource saved successfully!");

        return "redirect:/eduresources";
    }

    @GetMapping("/search")
    public String displaySearchResult(@RequestParam("searchResult") String searchResult, Model model) {

        List<EduResources> results = eduService.findResourceBySearch(searchResult);
        model.addAttribute("searchResult", searchResult);
        model.addAttribute("resources", results);

        return "eduresources/search";
    }

     // @GetMapping("/eduresources")
    // public String viewSavedResources()
    // {
    //     return "eduresources/savededuresources";
    // }

    // @GetMapping("/eduresources")
    // public String viewResourceInformation() {
    //     return "eduresources/info";
    // }
}