package org.example.groupproject.Full;

import org.example.groupproject.applicant.EmailService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class EmailTests {
    @Autowired
    private EmailService emailService;

    @Test
    public void testSendEmail() {
        emailService.sendEmail("tiago.moico@gmail.com", "Test Subject", "Test message from Spring app using Outlook");
    }
}
