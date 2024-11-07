package au.edu.rmit.sept.webapp.controllers;

import au.edu.rmit.sept.webapp.dto.AppointmentDTO;
import au.edu.rmit.sept.webapp.dto.PetDTO;
import au.edu.rmit.sept.webapp.models.Appointment;
import au.edu.rmit.sept.webapp.models.CustomUser;
import au.edu.rmit.sept.webapp.models.Pet;
import au.edu.rmit.sept.webapp.services.AppointmentService;
import au.edu.rmit.sept.webapp.services.CustomUserDetailsService;
import au.edu.rmit.sept.webapp.services.PetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.validation.Valid;



@Controller
@RequestMapping("/appointments")
public class AppointmentController {

    private final AppointmentService appointmentService;
    private final CustomUserDetailsService userService;
    private final PetService petService;

    @Autowired
    public AppointmentController(AppointmentService appointmentService, CustomUserDetailsService userService, PetService petService) {
        this.appointmentService = appointmentService;
        this.userService = userService;
        this.petService = petService;
    }

    // Display appointment booking form
    @GetMapping("/new")
    public String bookAppointmentForm(Model model) {
        // Create a new AppointmentDTO instance
        AppointmentDTO appointmentDTO = new AppointmentDTO();

        // Get the current logged-in user and their pets
        CustomUser currentUser = userService.getCurrentUser();
        List<PetDTO> petDTOs = petService.findPetsByUserId(currentUser.getUserId());
        List<Pet> pets = petDTOs.stream()
                .map(dto -> new Pet(dto.getPetId(), dto.getName(), dto.getSpecies(), dto.getBreed(), dto.getAge()))
                .collect(Collectors.toList());

        // Add appointmentDTO and pets list to the model
        model.addAttribute("appointment", appointmentDTO);
        if (pets.isEmpty()) {
            model.addAttribute("noPetsMessage", "You have no registered pets. Please register a pet before booking an appointment.");
        } else {
            model.addAttribute("pets", pets);
        }

        return "appointments/new";
    }

    // Handle form submission and save appointment
    @PostMapping("/book_appointment")
    public String bookAppointment(
    @Valid @ModelAttribute("appointment") AppointmentDTO appointmentDTO,
    BindingResult bindingResult,
    RedirectAttributes redirectAttributes,
    Model model) {
        // Get the current user
        CustomUser currentUser = userService.getCurrentUser();

        // Get the pet by petId from the form
        Optional<Pet> optionalPet = petService.findPetBypetId(appointmentDTO.getPetId());

        // Check if the pet exists
        if (optionalPet.isEmpty()) {
            bindingResult.rejectValue("petId", "error.appointmentDTO", "Pet not found.");
            model.addAttribute("pets", getCurrentUserPets());
            return "appointments/new";
        }

        // Validate appointment date
        LocalDate appointmentDate = appointmentDTO.getAppointmentDate();
        if (appointmentDate == null || appointmentDate.isBefore(LocalDate.now())) {
            bindingResult.rejectValue("appointmentDate", "error.appointmentDTO", "Appointment date cannot be in the past.");
        }

        // Validate appointment time
        LocalTime appointmentTime = appointmentDTO.getAppointmentTime();
        if (appointmentTime == null || appointmentTime.isBefore(LocalTime.of(8, 0)) || appointmentTime.isAfter(LocalTime.of(19, 0))) {
            bindingResult.rejectValue("appointmentTime", "error.appointmentDTO", "Appointment time must be between 8:00 AM and 7:00 PM.");
        }

        // Check for validation errors
        if (bindingResult.hasErrors()) {
            // Reload the booking form with error messages and pet list
            model.addAttribute("pets", getCurrentUserPets());
            return "appointments/new";
        }

        Pet pet = optionalPet.get();

        // Convert AppointmentDTO to Appointment entity
        Appointment appointment = new Appointment();
        appointment.setAppointmentDate(appointmentDate);
        appointment.setAppointmentTime(appointmentTime);
        appointment.setGeneralNotes(appointmentDTO.getGeneralNotes());
        appointment.setStatus("Scheduled");
        appointment.setUser(currentUser);
        appointment.setPet(pet);

        // Save the appointment using the service
        appointmentService.createAppointment(appointment);

        // Add a success message
        redirectAttributes.addFlashAttribute("message", "Appointment booked successfully!");

        // Redirect to the appointments page
        return "redirect:/appointments";
    }

    private List<Pet> getCurrentUserPets() {
        CustomUser currentUser = userService.getCurrentUser();
        List<PetDTO> petDTOs = petService.findPetsByUserId(currentUser.getUserId());
        return petDTOs.stream()
                .map(dto -> new Pet(dto.getPetId(), dto.getName(), dto.getSpecies(), dto.getBreed(), dto.getAge()))
                .collect(Collectors.toList());
    }
    @GetMapping("/view/{id}")
    public String viewSpecificAppointment(
            @PathVariable("id") Long appointmentId,
            Model model,
            RedirectAttributes redirectAttributes
    ) {
        // Retrieve the appointment by ID
        Optional<Appointment> optionalAppointment = appointmentService.findAppointmentById(appointmentId);

        if (optionalAppointment.isEmpty()) {
            redirectAttributes.addFlashAttribute("errorMessage", "Appointment not found.");
            return "redirect:/appointments";
        }

        Appointment appointment = optionalAppointment.get();

        // Check if the appointment is cancelled
        if ("Cancelled".equalsIgnoreCase(appointment.getStatus())) {
            redirectAttributes.addFlashAttribute("errorMessage", "Cannot view a cancelled appointment.");
            return "redirect:/appointments";
        }

        // Add the appointment to the model
        model.addAttribute("appointment", appointment);

        return "appointments/view";
    }

    @GetMapping
    public String viewAppointments(Model model) {
        // Get the current user
        CustomUser currentUser = userService.getCurrentUser();

        // Retrieve the user's appointments
        List<Appointment> appointments = appointmentService.getAppointmentsForUser(currentUser.getUserId());

        // Add appointments to the model
        model.addAttribute("appointments", appointments);

        return "appointments/index";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable("id") Long appointmentId, Model model, RedirectAttributes redirectAttributes) {
        // Get the current user
        CustomUser currentUser = userService.getCurrentUser();

        // Retrieve the appointment
        Optional<Appointment> optionalAppointment = appointmentService.findAppointmentById(appointmentId);

        if (optionalAppointment.isEmpty()) {
            redirectAttributes.addFlashAttribute("errorMessage", "Appointment not found.");
            return "redirect:/appointments";
        }

        Appointment appointment = optionalAppointment.get();

        if ("Cancelled".equalsIgnoreCase(appointment.getStatus())) {
            redirectAttributes.addFlashAttribute("errorMessage", "Cannot edit or view a cancelled appointment.");
            return "redirect:/appointments";
        }

        // Check if the appointment belongs to the current user
        if (!appointment.getUser().getUserId().equals(currentUser.getUserId())) {
            redirectAttributes.addFlashAttribute("errorMessage", "You are not authorised to edit this appointment.");
            return "redirect:/appointments";
        }

        // Convert Appointment to AppointmentDTO
        AppointmentDTO appointmentDTO = new AppointmentDTO();
        appointmentDTO.setAppointmentID(appointment.getAppointmentID());
        appointmentDTO.setAppointmentDate(appointment.getAppointmentDate());
        appointmentDTO.setAppointmentTime(appointment.getAppointmentTime());
        appointmentDTO.setGeneralNotes(appointment.getGeneralNotes());
        appointmentDTO.setPetId(appointment.getPet().getPetId());

        Pet pet = petService.findPetBypetId(appointmentDTO.getPetId()).orElse(null);

        if (pet != null) {
            String petName = pet.getName();
            model.addAttribute("petName", petName);
        } else {
            model.addAttribute("petName", "Pet not found");
        }

        // Add attributes to the model
        model.addAttribute("appointment", appointmentDTO);
        model.addAttribute("pets", getCurrentUserPets());

        return "appointments/edit";
    }

    @PostMapping("/edit/{id}")
    public String updateAppointment(
        @PathVariable("id") Long appointmentId,
        @Valid @ModelAttribute("appointment") AppointmentDTO appointmentDTO,
        BindingResult bindingResult,
        RedirectAttributes redirectAttributes,
        Model model
    ) {
        // Get the current user
        CustomUser currentUser = userService.getCurrentUser();

        // Retrieve the appointment
        Optional<Appointment> optionalAppointment = appointmentService.findAppointmentById(appointmentId);

        if (optionalAppointment.isEmpty()) {
            redirectAttributes.addFlashAttribute("errorMessage", "Appointment not found.");
            return "redirect:/appointments";
        }

        Appointment appointment = optionalAppointment.get();

        if ("Cancelled".equalsIgnoreCase(appointment.getStatus())) {
            redirectAttributes.addFlashAttribute("errorMessage", "Cannot edit or view a cancelled appointment.");
            return "redirect:/appointments";
        }

        // Check if the appointment belongs to the current user
        if (!appointment.getUser().getUserId().equals(currentUser.getUserId())) {
            redirectAttributes.addFlashAttribute("errorMessage", "You are not authorised to edit this appointment.");
            return "redirect:/appointments";
        }

        // Validate pet selection
        Optional<Pet> optionalPet = petService.findPetBypetId(appointmentDTO.getPetId());
        if (optionalPet.isEmpty()) {
            bindingResult.rejectValue("petId", "error.appointmentDTO", "Pet not found.");
        }

        // Validate appointment date
        LocalDate appointmentDate = appointmentDTO.getAppointmentDate();
        if (appointmentDate == null || appointmentDate.isBefore(LocalDate.now())) {
            bindingResult.rejectValue("appointmentDate", "error.appointmentDTO", "Appointment date cannot be in the past.");
        }

        // Validate appointment time
        LocalTime appointmentTime = appointmentDTO.getAppointmentTime();
        if (appointmentTime == null || appointmentTime.isBefore(LocalTime.of(8, 0)) || appointmentTime.isAfter(LocalTime.of(19, 0))) {
            bindingResult.rejectValue("appointmentTime", "error.appointmentDTO", "Appointment time must be between 8:00 AM and 7:00 PM.");
        }

        // Check for validation errors
        if (bindingResult.hasErrors()) {
            model.addAttribute("pets", getCurrentUserPets());
            return "appointments/edit";
        }

        Pet pet = optionalPet.get();

        // Update the appointment
        appointment.setAppointmentDate(appointmentDate);
        appointment.setAppointmentTime(appointmentTime);
        appointment.setGeneralNotes(appointmentDTO.getGeneralNotes());
        appointment.setPet(pet);

        // Save the updated appointment
        appointmentService.updateAppointment(appointment);

        // Add a success message
        redirectAttributes.addFlashAttribute("message", "Appointment updated successfully!");

        // Redirect to the appointments page
        return "redirect:/appointments";
    }

    @PostMapping("/cancel/{id}")
    public String cancelAppointment(@PathVariable("id") Long appointmentId, RedirectAttributes redirectAttributes) {
        // Get the current user
        CustomUser currentUser = userService.getCurrentUser();

        // Retrieve the appointment
        Optional<Appointment> optionalAppointment = appointmentService.findAppointmentById(appointmentId);

        if (optionalAppointment.isEmpty()) {
            redirectAttributes.addFlashAttribute("errorMessage", "Appointment not found.");
            return "redirect:/appointments";
        }

        Appointment appointment = optionalAppointment.get();

        if ("Cancelled".equalsIgnoreCase(appointment.getStatus())) {
            redirectAttributes.addFlashAttribute("errorMessage", "Appointment is already cancelled.");
            return "redirect:/appointments";
        }

        // Check if the appointment belongs to the current user
        if (!appointment.getUser().getUserId().equals(currentUser.getUserId())) {
            redirectAttributes.addFlashAttribute("errorMessage", "You are not authorised to cancel this appointment.");
            return "redirect:/appointments";
        }

        // Set the status to "Cancelled"
        appointment.setStatus("Cancelled");

        // Save the updated appointment
        appointmentService.saveAppointment(appointment);

        // Add a success message
        redirectAttributes.addFlashAttribute("message", "Appointment cancelled successfully!");

        // Redirect to the appointments page
        return "redirect:/appointments";
    }


}
