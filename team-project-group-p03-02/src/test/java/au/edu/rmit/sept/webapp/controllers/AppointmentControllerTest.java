package au.edu.rmit.sept.webapp.controllers;

import au.edu.rmit.sept.webapp.dto.AppointmentDTO;
import au.edu.rmit.sept.webapp.enums.UserType;
import au.edu.rmit.sept.webapp.models.Appointment;
import au.edu.rmit.sept.webapp.models.Clinic;
import au.edu.rmit.sept.webapp.models.CustomUser;
import au.edu.rmit.sept.webapp.models.Pet;
import au.edu.rmit.sept.webapp.services.AppointmentService;
import au.edu.rmit.sept.webapp.services.CustomUserDetailsService;
import au.edu.rmit.sept.webapp.services.PetService;
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
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AppointmentController.class)
public class AppointmentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AppointmentService appointmentService;

    @MockBean
    private CustomUserDetailsService userService;

    @MockBean
    private PetService petService;

    private CustomUser currentUser;

    @BeforeEach
    void setUp() {
        currentUser = new CustomUser();
        currentUser.setId(1L);
        currentUser.setName("Test User");
        currentUser.setUserType(UserType.Admin);
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(currentUser.getUserType().name()));
        Authentication authentication = new UsernamePasswordAuthenticationToken(currentUser, null, authorities);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        MockitoAnnotations.openMocks(this);
    }

    // @Test
    // void testBookAppointmentForm() throws Exception {
    //     // Mock the behavior of services
    //     when(userService.getCurrentUser()).thenReturn(currentUser);
    //     when(petService.findPetBypetId(anyLong())).thenReturn(Optional.of(new Pet()));

    //     // Perform the test
    //     mockMvc.perform(get("/appointments/new"))
    //             .andExpect(status().isOk())
    //             .andExpect(view().name("appointments/new"))
    //             .andExpect(model().attributeExists("appointment"))
    //             .andExpect(model().attribute("pets", new ArrayList<Pet>()));
    // }

    // 1
    @Test
    void testBookAppointment() throws Exception {
        // mock pet
        Pet mockPet = new Pet();
        mockPet.setPetId(1L);
        mockPet.setName("Buddy");

        // Mock the behavior of services
        when(userService.getCurrentUser()).thenReturn(currentUser);
        when(petService.findPetBypetId(anyLong())).thenReturn(Optional.of(mockPet));
        when(appointmentService.createAppointment(new Appointment())).thenReturn(new Appointment());

        LocalTime customTime = LocalTime.of(14, 30, 15);

        // Perform the test
        mockMvc.perform(post("/appointments/book_appointment")
                        .param("petId", "1")
                        .param("appointmentDate", LocalDate.now().toString())
                        .param("appointmentTime", customTime.toString())
                        .param("generalNotes", "Notes")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/appointments"))
                .andExpect(flash().attribute("message", "Appointment booked successfully!"));
    }

    // 2
    @Test
    void testViewSpecificAppointment() throws Exception {
        // mock pet
        Pet mockPet = new Pet();
        mockPet.setPetId(1L);
        mockPet.setName("Buddy");

        // mock appointment
        Appointment mockAppointment = new Appointment();
        mockAppointment.setAppointmentID(1L);
        mockAppointment.setUser(currentUser);
        mockAppointment.setPet(mockPet);
        mockAppointment.setStatus("Scheduled");

        when(appointmentService.findAppointmentById(1L)).thenReturn(Optional.of(mockAppointment));

        // Perform the test
        mockMvc.perform(get("/appointments/view/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("appointments/view"))
                .andExpect(model().attribute("appointment", mockAppointment));
    }

    // 3
     @Test
     void testViewAppointments() throws Exception {
         List<Appointment> appointments = new ArrayList<>();

         // Mock the behavior of services
         when(userService.getCurrentUser()).thenReturn(currentUser);
         when(appointmentService.getAppointmentsForUser(anyLong())).thenReturn(appointments);


         mockMvc.perform(get("/appointments"))
                 .andExpect(status().isOk())
                 .andExpect(view().name("appointments/index"))
                 .andExpect(model().attribute("appointments", appointments));
     }

     // 4
     @Test
     void testShowEditForm() throws Exception {
         // mock pet
         Pet mockPet = new Pet();
         mockPet.setPetId(1L);
         mockPet.setName("Buddy");

         // mock clinic
         Clinic mockClinic = new Clinic();
         mockClinic.setClinicId(1L);
         mockClinic.setName("Clinic");

         // mock appointment
         Appointment mockAppointment = new Appointment();
         mockAppointment.setAppointmentID(1L);
         mockAppointment.setUser(currentUser);
         mockAppointment.setPet(mockPet);
         mockAppointment.setClinic(mockClinic);
         mockAppointment.setStatus("Scheduled");

         when(userService.getCurrentUser()).thenReturn(currentUser);
         when(petService.findPetBypetId(anyLong())).thenReturn(Optional.of(mockPet));
         when(appointmentService.findAppointmentById(1L)).thenReturn(Optional.of(mockAppointment));

         mockMvc.perform(get("/appointments/edit/1"))
                 .andExpect(status().isOk())
                 .andExpect(view().name("appointments/edit"))
                 .andExpect(model().attributeExists("appointment"));
     }
     // 5
    @Test
    void testUpdateAppointment() throws Exception {
        // mock pet
        Pet mockPet = new Pet();
        mockPet.setPetId(1L);
        mockPet.setName("Buddy");

        // mock clinic
        Clinic mockClinic = new Clinic();
        mockClinic.setClinicId(1L);
        mockClinic.setName("Clinic");

        // mock appointment
        Appointment mockAppointment = new Appointment();
        mockAppointment.setAppointmentID(1L);
        mockAppointment.setUser(currentUser);
        mockAppointment.setPet(mockPet);
        mockAppointment.setClinic(mockClinic);
        mockAppointment.setStatus("Scheduled");

        LocalTime customTime = LocalTime.of(14, 30, 15);
        // Mock the behavior of services
        when(userService.getCurrentUser()).thenReturn(currentUser);
        when(appointmentService.findAppointmentById(1L)).thenReturn(Optional.of(mockAppointment));
        when(petService.findPetBypetId(anyLong())).thenReturn(Optional.of(mockPet));

        // Perform the test
        mockMvc.perform(post("/appointments/edit/1")
                        .param("petId", "1")
                        .param("appointmentDate", LocalDate.now().toString())
                        .param("appointmentTime", customTime.toString())
                        .param("generalNotes", "Updated Notes")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/appointments"))
                .andExpect(flash().attribute("message", "Appointment updated successfully!"));
    }
    // 6
    @Test
    void testDeleteAppointment() throws Exception {
        // mock appointment
        Appointment mockAppointment = new Appointment();
        mockAppointment.setAppointmentID(1L);
        mockAppointment.setUser(currentUser);
        mockAppointment.setStatus("Scheduled");

        // Mock the behavior of services
        when(userService.getCurrentUser()).thenReturn(currentUser);
        when(appointmentService.findAppointmentById(1L)).thenReturn(Optional.of(mockAppointment));

        mockMvc.perform(post("/appointments/cancel/1")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/appointments"))
                .andExpect(flash().attribute("message", "Appointment cancelled successfully!"));
    }
}
