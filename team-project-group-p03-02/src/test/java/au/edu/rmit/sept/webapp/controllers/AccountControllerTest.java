package au.edu.rmit.sept.webapp.controllers;

import au.edu.rmit.sept.webapp.models.User;
import au.edu.rmit.sept.webapp.services.SavedResourcesService;
import au.edu.rmit.sept.webapp.services.UserService;
import au.edu.rmit.sept.webapp.services.CustomUserDetailsService;
import au.edu.rmit.sept.webapp.services.EduResourcesService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AccountController.class)
public class AccountControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @MockBean
    private SavedResourcesService savedService;

    @MockBean
    private CustomUserDetailsService customUserService;

    @MockBean
    private EduResourcesService eduResourcesService; 

    private User mockUser;

    @BeforeEach
    void setUp() {
        mockUser = new User(1L, "Test User", "password", "testuser@example.com", "1234567890", "Test Address");
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
        Authentication authentication = new UsernamePasswordAuthenticationToken(mockUser, null, authorities);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        MockitoAnnotations.openMocks(this);
    }

    // Testcase 1: Testing if the account settings view is displayed correctly
    @Test
    void testViewAccountSettings() throws Exception {
        when(userService.getUserById(anyLong())).thenReturn(Optional.of(mockUser));
        mockMvc.perform(get("/account/settings"))
                .andExpect(status().isOk())
                .andExpect(view().name("account/settings"))
                .andExpect(model().attributeExists("user"));
    }

    // Testcase 2: Testing if contact information is successfully updated
    @Test
    void testUpdateContactInfo() throws Exception {
        when(userService.getUserById(anyLong())).thenReturn(Optional.of(mockUser));
        UserDetails mockUserDetails = org.mockito.Mockito.mock(UserDetails.class);
        when(userService.loadUserByUsername(anyString())).thenReturn(mockUserDetails);
        when(userService.updateContactInfo(anyLong(), any(User.class))).thenReturn(mockUser);
        mockMvc.perform(post("/account/update-contact-info")
                        .param("name", "Updated Name")
                        .param("email", "updatedemail@example.com")
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name("account/settings"))
                .andExpect(model().attributeExists("successMessage"));
    }

    // Testcase 3: Testing if the password change is successful
    @Test
    void testChangePasswordSuccess() throws Exception {
        when(userService.getUserById(anyLong())).thenReturn(Optional.of(mockUser));
        when(userService.changePassword(anyLong(), anyString(), anyString())).thenReturn(true);
        UserDetails mockUserDetails = org.mockito.Mockito.mock(UserDetails.class);
        when(userService.loadUserByUsername(anyString())).thenReturn(mockUserDetails);
        when(mockUserDetails.getAuthorities()).thenReturn(new ArrayList<>());
        mockMvc.perform(post("/account/change-password")
                        .param("oldPassword", "password")
                        .param("newPassword", "newPassword")
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name("account/settings"))
                .andExpect(model().attributeExists("successMessage"));
    }

    // Testcase 4: Testing if the password change fails when the old password is incorrect
    @Test
    void testChangePasswordFail() throws Exception {
        when(userService.getUserById(anyLong())).thenReturn(Optional.of(mockUser));
        when(userService.changePassword(anyLong(), anyString(), anyString())).thenReturn(false);
        mockMvc.perform(post("/account/change-password")
                        .param("oldPassword", "wrongPassword")
                        .param("newPassword", "newPassword")
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name("account/settings"))
                .andExpect(model().attributeExists("errorMessage"));
    }

    // Testcase 5: Testing if the account is deleted successfully when the correct password is provided
    @Test
    void testDeleteAccountSuccess() throws Exception {
        when(userService.getUserById(anyLong())).thenReturn(Optional.of(mockUser));
        when(userService.deleteAccount(anyLong(), anyString())).thenReturn(true);
        mockMvc.perform(post("/account/delete-account")
                        .param("password", "password")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));
    }

    // Testcase 6: Testing if account deletion fails when the wrong password is provided
    @Test
    void testDeleteAccountFail() throws Exception {
        when(userService.getUserById(anyLong())).thenReturn(Optional.of(mockUser));
        when(userService.deleteAccount(anyLong(), anyString())).thenReturn(false);
        mockMvc.perform(post("/account/delete-account")
                        .param("password", "wrongPassword")
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(view().name("account/settings"))
                .andExpect(model().attributeExists("errorMessage"));
    }
}
