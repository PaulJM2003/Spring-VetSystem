package au.edu.rmit.sept.webapp.services;

import au.edu.rmit.sept.webapp.models.User;
import au.edu.rmit.sept.webapp.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByName(username);
        if (user.isEmpty()) {
            throw new UsernameNotFoundException("User not found");
        }
        return new org.springframework.security.core.userdetails.User(
            user.get().name(),
            user.get().password(),
            new ArrayList<>() 
        );
    }

    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }
    public Long getUserIdByUsername(String username) {
        Optional<User> userOpt = userRepository.findByName(username);
        if (userOpt.isPresent()) {
            return userOpt.get().userID();
        } else {
            throw new RuntimeException("User not found");
        }
    }

    public User updateContactInfo(Long userID, User updatedUser) {
        Optional<User> existingUser = userRepository.findById(userID);
        if (existingUser.isPresent()) {
            User user = existingUser.get();
            User updated = new User(
                user.userID(),
                updatedUser.name(),
                user.password(),
                updatedUser.email(),
                updatedUser.phoneNumber(),
                updatedUser.address()
            );
            userRepository.save(updated);
            return updated;
        } else {
            throw new RuntimeException("User not found");
        }
    }

    public boolean changePassword(Long userID, String oldPassword, String newPassword) {
        Optional<User> userOpt = userRepository.findById(userID);
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            if (passwordEncoder.matches(oldPassword, user.password())) {
                String encodedPassword = passwordEncoder.encode(newPassword);
                userRepository.updatePassword(userID, encodedPassword);
                return true;
            }
        }
        return false; 
    }

    public boolean deleteAccount(Long userID, String password) {
        Optional<User> userOpt = userRepository.findById(userID);
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            if (passwordEncoder.matches(password, user.password())) {
                userRepository.delete(userID);
                return true;
            }
        }
        return false;
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
}
