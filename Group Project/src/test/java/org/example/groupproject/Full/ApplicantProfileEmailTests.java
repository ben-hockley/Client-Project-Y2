package org.example.groupproject.Full;

import org.example.groupproject.applicant.EmailController;
import org.example.groupproject.applicant.EmailRequest;
import org.example.groupproject.applicant.EmailService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class ApplicantProfileEmailTests {

    @Mock
    private JavaMailSender mailSender;
    @InjectMocks
    private EmailService emailService1;

    @Mock
    private EmailService emailService2;

    @InjectMocks
    private EmailController emailController;

    @Test
    public void testSendEmailService() {
        String to = "test@example.com";
        String subject = "Test Subject";
        String text = "Test Message";

        emailService1.sendEmail(to, subject, text);

        ArgumentCaptor<SimpleMailMessage> messageCaptor = ArgumentCaptor.forClass(SimpleMailMessage.class);
        verify(mailSender).send(messageCaptor.capture());

        SimpleMailMessage capturedMessage = messageCaptor.getValue();
        assertEquals(to, capturedMessage.getTo()[0]);
        assertEquals(subject, capturedMessage.getSubject());
        assertEquals(text, capturedMessage.getText());
        assertEquals("groupProjectTeam11@outlook.com", capturedMessage.getFrom());
    }

    @Test
    public void testSendEmailController() {
        EmailRequest emailRequest = new EmailRequest("test@example.com", "Test Subject", "Test Message");

        ResponseEntity<String> response = emailController.sendEmail(emailRequest);

        assertEquals("Email sent successfully", response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());

        verify(emailService2).sendEmail(emailRequest.getTo(), emailRequest.getSubject(), emailRequest.getMessage());
    }

    @Test
    public void testSendEmailWithException() {
        EmailRequest emailRequest = new EmailRequest("test@example.com", "Test Subject", "Test Message");

        doThrow(new RuntimeException("Service error")).when(emailService2).sendEmail(emailRequest.getTo(), emailRequest.getSubject(), emailRequest.getMessage());

        assertThrows(RuntimeException.class, () -> {
            emailController.sendEmail(emailRequest);
        });
    }

    @Test
    public void testNoArgsConstructor() {
        EmailRequest emailRequest = new EmailRequest();
        assertEquals(null, emailRequest.getTo());
        assertEquals(null, emailRequest.getSubject());
        assertEquals(null, emailRequest.getMessage());
    }

    @Test
    public void testAllArgsConstructor() {
        EmailRequest emailRequest = new EmailRequest("test@example.com", "Test Subject", "Test Message");
        assertEquals("test@example.com", emailRequest.getTo());
        assertEquals("Test Subject", emailRequest.getSubject());
        assertEquals("Test Message", emailRequest.getMessage());
    }

    @Test
    public void testSettersAndGetters() {
        EmailRequest emailRequest = new EmailRequest();
        emailRequest.setTo("test@example.com");
        emailRequest.setSubject("Test Subject");
        emailRequest.setMessage("Test Message");

        assertEquals("test@example.com", emailRequest.getTo());
        assertEquals("Test Subject", emailRequest.getSubject());
        assertEquals("Test Message", emailRequest.getMessage());
    }
}
