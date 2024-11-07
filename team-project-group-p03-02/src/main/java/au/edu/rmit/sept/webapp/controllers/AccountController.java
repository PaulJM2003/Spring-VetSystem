package au.edu.rmit.sept.webapp.controllers;

import au.edu.rmit.sept.webapp.models.CustomUser;
import au.edu.rmit.sept.webapp.models.EduResources;
import au.edu.rmit.sept.webapp.models.User;
import au.edu.rmit.sept.webapp.services.CustomUserDetailsService;
import au.edu.rmit.sept.webapp.services.EduResourcesService;
import au.edu.rmit.sept.webapp.services.SavedResourcesService;
import au.edu.rmit.sept.webapp.services.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import java.util.List;
import au.edu.rmit.sept.webapp.models.SavedResources;
import java.util.ArrayList;
import java.util.Optional;


@Controller
@RequestMapping("/account")
public class AccountController {

    private final UserService userService;
    private final SavedResourcesService savedService;
    private final CustomUserDetailsService customUserService;
    private final EduResourcesService eduService;

    @Autowired
    public AccountController(UserService userService, SavedResourcesService savedService, CustomUserDetailsService customUserService, EduResourcesService eduService) {
        this.userService = userService;
        this.savedService = savedService;
        this.customUserService = customUserService;
        this.eduService = eduService;
    }

    // This method retrieves the ID of the currently authenticated user
    private Long getAuthenticatedUserId() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();  
        return userService.getUserIdByUsername(username);
    }

    // Handles GET requests to display the account settings page for the authenticated user
    @GetMapping("/settings")
    public String accountSettings(Model model) {
        Long userID = getAuthenticatedUserId();  
        userService.getUserById(userID).ifPresent(user -> model.addAttribute("user", user));
        return "account/settings";
    }

    // Handles POST requests to update the contact information of the authenticated user.
    @PostMapping("/update-contact-info")
    public String updateContactInfo(@ModelAttribute("user") User updatedUser, Model model) {
        Long userID = getAuthenticatedUserId();  
    
        // fetches the current user from the database
        Optional<User> existingUserOpt = userService.getUserById(userID);
    
        if (existingUserOpt.isPresent()) {
            User existingUser = existingUserOpt.get();
    
            // creates a new user object to merge existing data with the updated data
            User updated = new User(
                existingUser.userID(),
                updatedUser.name() != null ? updatedUser.name() : existingUser.name(),  
                existingUser.password(), 
                updatedUser.email() != null ? updatedUser.email() : existingUser.email(),  
                updatedUser.phoneNumber() != null ? updatedUser.phoneNumber() : existingUser.phoneNumber(),  
                updatedUser.address() != null ? updatedUser.address() : existingUser.address()  
            );
            // saves the updated user back to the database
            userService.updateContactInfo(userID, updated);
            // refresh the model with updated user data
            model.addAttribute("user", updated);
            model.addAttribute("successMessage", "Contact information updated successfully."); 
            // update the security context with the new username
            UserDetails userDetails = userService.loadUserByUsername(updated.name());
            Authentication newAuth = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(newAuth);
        } else {
            model.addAttribute("errorMessage", "User not found. Please try again.");
        }
    
        return "account/settings";  
    }
        
    @PostMapping("/change-password")
    public String changePassword(@RequestParam("oldPassword") String oldPassword, 
                                 @RequestParam("newPassword") String newPassword, 
                                 Model model) {
        Long userID = getAuthenticatedUserId();
        // verifies the old password and change the new password if correct
        if (userService.changePassword(userID, oldPassword, newPassword)) {
            model.addAttribute("successMessage", "Password changed successfully.");
            // reauthenticate user with the updated password
            Optional<User> updatedUserOpt = userService.getUserById(userID);
            if (updatedUserOpt.isPresent()) {
                User updatedUser = updatedUserOpt.get();
                // loads the updated UserDetails
                UserDetails userDetails = userService.loadUserByUsername(updatedUser.name());
                // updates the authentication with the new password
                Authentication newAuth = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(newAuth);
            }
        } else {
            model.addAttribute("errorMessage", "Invalid current password.");
        }
        // refetches the user to display updated information and pass it to the model
        userService.getUserById(userID).ifPresent(user -> model.addAttribute("user", user));
        return "account/settings";  
    }
    
    @PostMapping("/delete-account")
    public String deleteAccount(@RequestParam("password") String password, HttpServletRequest request, Model model) {
        Long userID = getAuthenticatedUserId();
        if (userService.deleteAccount(userID, password)) {
            // logs the user out programmatically
            SecurityContextHolder.clearContext();  
            // invalidates the session to ensure the user is fully logged out
            request.getSession().invalidate(); 
            return "redirect:/"; 
        } else {
            model.addAttribute("errorMessage", "Invalid password. Account deletion failed.");
            // refetches the user to display the updated account settings view with the error message
            userService.getUserById(userID).ifPresent(user -> model.addAttribute("user", user));
            return "account/settings";  
        }
    }

    @GetMapping("/savededuresources")
    public String viewSavedResources(Model model) {
        //convert saved resources to eduresources
        List<SavedResources> savedResources = savedService.findAllSavedResources();
        List<EduResources> eduResources = new ArrayList<>();
        for (int i=0; i<savedResources.size(); i++) {
            // eduService.findResourceById(savedResources.get(i).getResources().getResourceID);
            eduResources.add(savedResources.get(i).getResources());
        }

        model.addAttribute("resources", eduResources);
        return "account/savededuresources";
    }

    @PostMapping("/delete_resource")
    public String deleteResource(@RequestParam("resourceID") Long resourceID, RedirectAttributes redirectAttributes) {
        CustomUser currentUser = customUserService.getCurrentUser();
        EduResources resource = eduService.findResourceById(resourceID);

        savedService.deleteSavedResource(currentUser, resource);
        redirectAttributes.addFlashAttribute("message", "Resource removed successfully!");
        return "redirect:/account/savededuresources";
    }
}

