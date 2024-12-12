package org.example.groupproject.Full;

import org.example.groupproject.applicant.ApplicantEditController;
import org.example.groupproject.applicant.ApplicantRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import static org.junit.jupiter.api.Assertions.*;

public class ApplicantEditControllerTests {
    @Mock
    private ApplicantRepository applicantRepository;

    @Mock
    private Model model;

    @Mock
    private BindingResult bindingResult;

    @InjectMocks
    private ApplicantEditController applicantEditController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void deleteApplicantTest() {
        Integer applicantId = 5;
        String result = applicantEditController.deleteApplicant(applicantId);
        assertEquals("redirect:/applicants/all", result);
    }

    @Test
    public void favouriteApplicantTest() {
        Integer applicantId = 5;
        String result = applicantEditController.favouriteApplicant(applicantId);
        assertEquals("redirect:/applicants/all", result);
    }
}
