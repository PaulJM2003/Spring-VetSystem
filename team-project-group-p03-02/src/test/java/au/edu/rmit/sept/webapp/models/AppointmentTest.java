// package au.edu.rmit.sept.webapp.models;

// import java.time.LocalDate;
// import java.time.LocalTime;

// import org.junit.jupiter.api.Test;
// import static org.junit.jupiter.api.Assertions.*;

// public class AppointmentTest {

//     @Test
//     public void testToString() {
//         CustomUser user = new CustomUser();
//         user.setId(1L);

//         Pet pet = new Pet();
//         pet.setpetId(2L);

//         Clinic clinic = new Clinic();
//         clinic.setClinicId(1L);

//         Appointment appointment = new Appointment();
//         appointment.setAppointmentID(1L);
//         appointment.setUser(user);
//         appointment.setPet(pet);
//         appointment.setClinic(clinic);
//         appointment.setAppointmentDate(LocalDate.of(2024, 9, 19));
//         appointment.setStatus("Scheduled");
//         appointment.setGeneralNotes("Some notes");
//         appointment.setFees(100.0f);
//         appointment.setAppointmentTime(LocalTime.of(14, 30));

//         String expectedToString = "Appointment{" +
//                 "appointmentID=1" +
//                 ", userID='1'" +
//                 ", petId='2'" +
//                 ", clinicID='1'" +
//                 ", appointmentDate=2024-09-19" +
//                 ", status='Scheduled'" +
//                 ", generalNotes='Some notes'" +
//                 ", fees=100.0" +
//                 ", appointmentTime=14:30" +
//                 '}';

//         assertEquals(expectedToString, appointment.toString());
//     }
// }
