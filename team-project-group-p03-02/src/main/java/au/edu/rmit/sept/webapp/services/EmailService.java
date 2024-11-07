package au.edu.rmit.sept.webapp.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException; 
import jakarta.mail.internet.MimeMessage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender javaMailSender;

    public void sendHealthRecord(String toEmail, String petName, ByteArrayOutputStream healthRecordPdf) throws MessagingException, IOException {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        helper.setTo(toEmail);
        helper.setSubject("Health Records for " + petName);
        helper.setText("Please find attached the health records for your pet " + petName);

        // Attach the health record PDF
        ByteArrayResource resource = new ByteArrayResource(healthRecordPdf.toByteArray());
        helper.addAttachment("HealthRecords_" + petName + ".pdf", resource);

        // Send the email
        javaMailSender.send(message);
    }
}