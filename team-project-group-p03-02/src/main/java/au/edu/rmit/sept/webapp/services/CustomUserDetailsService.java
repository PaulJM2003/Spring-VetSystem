package au.edu.rmit.sept.webapp.services;

import au.edu.rmit.sept.webapp.enums.UserType;
import au.edu.rmit.sept.webapp.models.CustomUser;
import au.edu.rmit.sept.webapp.repositories.CustomUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    private CustomUserRepository customUserRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        CustomUser user = customUserRepository.findByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException("User not found with email: " + email);
        }

        return User.builder()
                .username(user.getName())
                .password(user.getPassword())
                .roles(user.getUserType().name())
                .build();
    }
    // Get the currently authenticated user
    public CustomUser getCurrentUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof UserDetails) {
            String username = ((UserDetails) principal).getUsername();
            return customUserRepository.findByName(username);
        } else {
            throw new RuntimeException("User is not authenticated");
        }
    }

    public List<CustomUser> getVets() {

        //find all vetIDs whos usertype is 'Vet'
        List<Long> vetIDs = customUserRepository.findVetID(UserType.Vet);
        List<CustomUser> vets = new ArrayList<>();

        //use vetIDs found to retrieve vet objects
        for (int i=0; i<vetIDs.size(); i++) {
            CustomUser user = customUserRepository.findById(vetIDs.get(i)).orElse(null);
            vets.add(user);
        }
        return vets;
    }

    public CustomUser findVetByName(String name) {

        //return vet object using their name
        Long vetID = customUserRepository.findVetIDByName(name);
        return customUserRepository.findById(vetID)
        .orElseThrow(() -> new NoSuchElementException("Clinic not found with id " + vetID));
    }
}
