package org.example.groupproject.applicant;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@Repository
public class NewsletterRepository {

    private final JdbcTemplate jdbcTemplate;
    private static final Logger logger = LoggerFactory.getLogger(NewsletterRepository.class);

    public NewsletterRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // Fetch emails based on the "emailToAll" parameter
    public List<String> getEmails(boolean emailToAll) {
        String sql;

        // If emailToAll is true, fetch emails from the Applicants table
        if (emailToAll) {
            sql = "SELECT email FROM applicants";  // Assuming the Applicants table is named 'applicants'
        } else {
            // Otherwise, fetch emails from the Newsletter table
            sql = "SELECT email FROM newsletter";  // Assuming the Newsletter table is named 'newsletter'
        }

        try {
            // Execute the query and return the list of emails
            List<String> emails = jdbcTemplate.queryForList(sql, String.class);

            if (emails.isEmpty()) {
                logger.warn("No emails found for the flag emailToAll={}", emailToAll);
            } else {
                logger.info("Fetched {} emails for emailToAll={}", emails.size(), emailToAll);
            }

            return emails;
        } catch (Exception e) {
            // Log the exception and rethrow it
            logger.error("Error fetching emails for emailToAll={}", emailToAll, e);
            throw new RuntimeException("Failed to fetch emails from the database", e);
        }
    }
}
