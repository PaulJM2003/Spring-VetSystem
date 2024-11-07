package au.edu.rmit.sept.webapp.services;

import au.edu.rmit.sept.webapp.models.Appointment;
import au.edu.rmit.sept.webapp.repositories.AppointmentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class AppointmentServiceImplTest {

    @Mock
    private AppointmentRepository appointmentRepository;

    @InjectMocks
    private AppointmentServiceImpl appointmentService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateAppointment() {
        Appointment mockAppointment = new Appointment();
        mockAppointment.setAppointmentID(1L);
        mockAppointment.setAppointmentDate(LocalDate.now());
        mockAppointment.setAppointmentTime(LocalTime.now());
        mockAppointment.setGeneralNotes("Notes");
        mockAppointment.setStatus("Scheduled");

        when(appointmentRepository.save(mockAppointment)).thenReturn(mockAppointment);

        Appointment appointment = appointmentService.createAppointment(mockAppointment);

        assertEquals(mockAppointment, appointment);
    }

    @Test
     void testGetAppointmentsForUser() {
        // mock appointment
        Appointment mockAppointment = new Appointment();
        mockAppointment.setAppointmentID(1L);
        mockAppointment.setStatus("Scheduled");

         when(appointmentRepository.findByUserUserId(anyLong())).thenReturn(List.of(mockAppointment));

         List<Appointment> appointments = appointmentService.getAppointmentsForUser(1L);

         assertEquals(1, appointments.size());
     }

     @Test
     void testFindAppointmentById() {
            Appointment mockAppointment = new Appointment();
            mockAppointment.setAppointmentID(1L);
            mockAppointment.setAppointmentDate(LocalDate.now());
            mockAppointment.setAppointmentTime(LocalTime.now());
            mockAppointment.setGeneralNotes("Notes");
            mockAppointment.setStatus("Scheduled");

            when(appointmentRepository.findById(1L)).thenReturn(Optional.of(mockAppointment));

            Optional<Appointment> appointment = appointmentService.findAppointmentById(1L);

            assertEquals(mockAppointment, appointment.get());
     }

     @Test
     void testSaveAppointment() {
        Appointment mockAppointment = new Appointment();
        mockAppointment.setAppointmentID(1L);
        mockAppointment.setAppointmentDate(LocalDate.now());
        mockAppointment.setAppointmentTime(LocalTime.now());
        mockAppointment.setGeneralNotes("Notes");
        mockAppointment.setStatus("Scheduled");

        appointmentService.saveAppointment(mockAppointment);

        verify(appointmentRepository).save(mockAppointment);
     }
}

