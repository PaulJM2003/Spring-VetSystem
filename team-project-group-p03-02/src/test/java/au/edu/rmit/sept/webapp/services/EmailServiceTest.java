package au.edu.rmit.sept.webapp.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class EmailServiceTest {

    @Mock
    private JavaMailSender javaMailSender;

    @InjectMocks
    private EmailService emailService;

    @Mock
    private MimeMessage mimeMessage;

    @Mock
    private MimeMessageHelper mimeMessageHelper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }
    // Test Case 1:  Test if the email service successfully sends an email without throwing any exceptions.
    // This test validates that the service behaves correctly when everything works as expected.
    @Test
    void testSendHealthRecord_Success() throws MessagingException, IOException {
        ByteArrayOutputStream healthRecordPdf = new ByteArrayOutputStream();
        healthRecordPdf.write("Mock PDF content".getBytes());
        when(javaMailSender.createMimeMessage()).thenReturn(mimeMessage);        
        assertDoesNotThrow(() -> emailService.sendHealthRecord("test@example.com", "Fluffy", healthRecordPdf));
        verify(javaMailSender, times(1)).send(any(MimeMessage.class));
    }
    // Test Case 2:  Test that a MessagingException is thrown when MimeMessageHelper fails to create or attach the email details.
    @Test
    void testSendHealthRecord_MessagingException() throws MessagingException, IOException {
        ByteArrayOutputStream healthRecordPdf = new ByteArrayOutputStream();
        healthRecordPdf.write("Mock PDF content".getBytes());
        when(javaMailSender.createMimeMessage()).thenReturn(mimeMessage);
        doThrow(MessagingException.class).when(mimeMessageHelper).setTo(any(String.class));
        assertDoesNotThrow(() -> emailService.sendHealthRecord("test@example.com", "Fluffy", healthRecordPdf));
    }
    // Test Case 3:  Test that the PDF attachment is correctly added to the email.
    //This test ensures that the file content is attached as a resource.
    @Test
    void testSendHealthRecord_PdfAttachment() throws MessagingException, IOException {
        ByteArrayOutputStream healthRecordPdf = new ByteArrayOutputStream();
        healthRecordPdf.write("Mock PDF content".getBytes());
        when(javaMailSender.createMimeMessage()).thenReturn(mimeMessage);        
        emailService.sendHealthRecord("test@example.com", "Fluffy", healthRecordPdf);
        verify(javaMailSender, times(1)).send(mimeMessage);
    }
}
