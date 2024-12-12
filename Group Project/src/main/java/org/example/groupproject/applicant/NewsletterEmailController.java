package org.example.groupproject.applicant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@RestController
@RequestMapping("/newsletter")
public class NewsletterEmailController {

    @Autowired
    private NewsletterRepository newsletterRepository; // Repository for both Newsletter and Applicant emails

    private static final Logger logger = LoggerFactory.getLogger(NewsletterEmailController.class);

    // Endpoint to fetch emails based on the emailToAll flag
    @GetMapping("/getEmails")
    public ResponseEntity<List<String>> getEmails(@RequestParam("emailToAll") boolean emailToAll) {
        logger.info("Received request to fetch emails with emailToAll={}", emailToAll);

        try {
            // Fetch emails based on the emailToAll flag
            List<String> emails = newsletterRepository.getEmails(emailToAll);

            // Log the emails for debugging purposes (don't log sensitive data in production)
            logger.debug("Fetched emails: {}", emails);

            // Return the emails in the response
            return ResponseEntity.ok(emails);
        } catch (Exception e) {
            logger.error("Error fetching emails", e);

            // Return an error response
            return ResponseEntity.status(500).body(null);
        }
    }
}
