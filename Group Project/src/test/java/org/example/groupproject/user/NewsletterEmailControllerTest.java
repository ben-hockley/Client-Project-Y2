package org.example.groupproject.user;

import org.example.groupproject.applicant.NewsletterEmailController;
import org.example.groupproject.applicant.NewsletterRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class NewsletterEmailControllerTest {

    @Mock
    private NewsletterRepository newsletterRepository;

    @InjectMocks
    private NewsletterEmailController newsletterEmailController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldReturnEmails_WhenEmailToAllIsTrue() {
        boolean emailToAll = true;
        List<String> mockEmails = Arrays.asList("test1@example.com", "test2@example.com");
        when(newsletterRepository.getEmails(emailToAll)).thenReturn(mockEmails);

        ResponseEntity<List<String>> response = newsletterEmailController.getEmails(emailToAll);

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(mockEmails, response.getBody());
        verify(newsletterRepository, times(1)).getEmails(emailToAll);
    }

    @Test
    void shouldReturnEmptyList_WhenEmailToAllIsFalse() {
        boolean emailToAll = false;
        List<String> mockEmails = Collections.emptyList();
        when(newsletterRepository.getEmails(emailToAll)).thenReturn(mockEmails);

        ResponseEntity<List<String>> response = newsletterEmailController.getEmails(emailToAll);

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(mockEmails, response.getBody());
        verify(newsletterRepository, times(1)).getEmails(emailToAll);
    }

    @Test
    void shouldHandleEmptyEmailsList() {
        boolean emailToAll = true;
        when(newsletterRepository.getEmails(emailToAll)).thenReturn(Collections.emptyList());

        ResponseEntity<List<String>> response = newsletterEmailController.getEmails(emailToAll);

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().isEmpty());
        verify(newsletterRepository, times(1)).getEmails(emailToAll);
    }

    @Test
    void shouldLogProperly_WhenFetchingEmails() {
        boolean emailToAll = true;
        List<String> mockEmails = Arrays.asList("test1@example.com", "test2@example.com");
        when(newsletterRepository.getEmails(emailToAll)).thenReturn(mockEmails);

        ResponseEntity<List<String>> response = newsletterEmailController.getEmails(emailToAll);

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(mockEmails, response.getBody());
        verify(newsletterRepository, times(1)).getEmails(emailToAll);

    }
}
