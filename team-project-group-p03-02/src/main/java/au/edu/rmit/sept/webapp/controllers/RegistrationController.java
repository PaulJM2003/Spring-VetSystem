package au.edu.rmit.sept.webapp.controllers;

import au.edu.rmit.sept.webapp.models.CustomUser;
import au.edu.rmit.sept.webapp.repositories.CustomUserRepository;
import au.edu.rmit.sept.webapp.enums.UserType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class RegistrationController {
    @Autowired
    private CustomUserRepository customUserRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/register")
    public String register(Model model) {
        model.addAttribute("user", new CustomUser());
        return "authentication/register";
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute CustomUser user, Model model) {
        if (customUserRepository.findByEmail(user.getEmail()) != null) {
            model.addAttribute("errorMessage", "Email already exists.");
            return "authentication/register";
        }

        // Setup user model and save to repository
        user.setUserType(UserType.PetOwner);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        customUserRepository.save(user);

        return "redirect:/login";
    }
}
