package org.example.groupproject.user;

import org.example.groupproject.applicant.NewsletterRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class NewsletterRepositoryTest {

    @Mock
    private JdbcTemplate jdbcTemplate;

    @InjectMocks
    private NewsletterRepository newsletterRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldReturnEmails_WhenEmailToAllIsTrue_AndEmailsExist() {
        testGetEmails(
                true,
                "SELECT email FROM applicants",
                Arrays.asList("user1@example.com", "user2@example.com")
        );
    }

    @Test
    void shouldReturnEmails_WhenEmailToAllIsFalse_AndEmailsExist() {
        testGetEmails(
                false,
                "SELECT email FROM newsletter",
                Arrays.asList("user3@example.com", "user4@example.com")
        );
    }

    @Test
    void shouldReturnEmptyList_WhenEmailToAllIsTrue_AndNoEmailsExist() {
        testGetEmails(true, "SELECT email FROM applicants", Collections.emptyList());
    }

    @Test
    void shouldReturnEmptyList_WhenEmailToAllIsFalse_AndNoEmailsExist() {
        testGetEmails(false, "SELECT email FROM newsletter", Collections.emptyList());
    }

    @Test
    void shouldThrowException_WhenDatabaseErrorOccurs() {
        boolean emailToAll = true;
        String query = "SELECT email FROM applicants";

        when(jdbcTemplate.queryForList(query, String.class)).thenThrow(new RuntimeException("Database error"));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> newsletterRepository.getEmails(emailToAll));

        assertEquals("Failed to fetch emails from the database", exception.getMessage());
        verify(jdbcTemplate, times(1)).queryForList(query, String.class);
    }

    private void testGetEmails(boolean emailToAll, String query, List<String> expectedData) {
        when(jdbcTemplate.queryForList(query, String.class)).thenReturn(expectedData);

        List<String> result = newsletterRepository.getEmails(emailToAll);

        assertNotNull(result);
        assertEquals(expectedData.size(), result.size());
        assertEquals(expectedData, result);
        verify(jdbcTemplate, times(1)).queryForList(query, String.class);
    }
}
