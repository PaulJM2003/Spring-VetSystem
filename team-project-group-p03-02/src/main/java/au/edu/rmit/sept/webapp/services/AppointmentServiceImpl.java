package au.edu.rmit.sept.webapp.services;

import au.edu.rmit.sept.webapp.models.Appointment;
import au.edu.rmit.sept.webapp.repositories.AppointmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class AppointmentServiceImpl implements AppointmentService {

    private final AppointmentRepository appointmentRepository;

    @Autowired
    public AppointmentServiceImpl(AppointmentRepository appointmentRepository) {
        this.appointmentRepository = appointmentRepository;
    }

    @Override
    public Appointment createAppointment(Appointment appointment) {
        return appointmentRepository.save(appointment);
    }

    @Override
    public List<Appointment> getAppointmentsForUser(Long userId) {
        return appointmentRepository.findByUserUserId(userId);
    }

    @Override
    public Optional<Appointment> findAppointmentById(Long appointmentId) {
        return appointmentRepository.findById(appointmentId);
    }
    
    @Override
    public void updateAppointment(Appointment appointment) {
        appointmentRepository.save(appointment);
    }

    @Override
    public void saveAppointment(Appointment appointment) {
        appointmentRepository.save(appointment);
    }
    
    @Override
    public void updateAppointmentStatus(Appointment appointment) {
        if ("cancelled".equalsIgnoreCase(appointment.getStatus())) {
            // Do not change the status if it's already cancelled
            return;
        }

        LocalDate appointmentDate = appointment.getAppointmentDate();
        LocalDate today = LocalDate.now();

        if (appointmentDate.isBefore(today)) {
            appointment.setStatus("Completed");
        } else {
            appointment.setStatus("Upcoming");
        }

        // Optionally save the updated status to the database
        appointmentRepository.save(appointment);
    }
}
