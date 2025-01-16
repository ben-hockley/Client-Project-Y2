package org.example.groupproject.user;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.example.groupproject.applicant.EventApplicantForm;
import org.example.groupproject.applicant.EventApplicantFormRepository;
import org.example.groupproject.applicant.EventApplicantFormService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc  // This annotation ensures MockMvc is configured for the test
public class EventApplicantTests {

    @Autowired
    private Validator validator;

    @MockBean
    private EventApplicantFormRepository repository;

    @Autowired
    private EventApplicantFormService service;

    @Autowired
    private MockMvc mockMvc;

    private EventApplicantForm validForm;

    @BeforeEach
    void setup() {
        validForm = new EventApplicantForm();
        validForm.setName("John Doe");
        validForm.setEmail("john.doe@example.com");
        validForm.setInterest("Technology");
        validForm.setSpokeTo("Jane Smith");
        validForm.setEventAttended("Tech Summit 2025");
    }

    // Entity Validation Tests
    @Test
    void testValidEventApplicantForm() {
        Set<ConstraintViolation<EventApplicantForm>> violations = validator.validate(validForm);
        assertThat(violations).isEmpty();
    }

    @Test
    void testInvalidEventApplicantForm() {
        validForm.setEmail("invalid-email"); // Invalid email
        Set<ConstraintViolation<EventApplicantForm>> violations = validator.validate(validForm);
        assertThat(violations).isNotEmpty();
    }

    // Controller Tests
    @Test
    @WithMockUser(username = "user", roles = {"USER"})  // Simulating an authenticated user
    void testShowForm() throws Exception {
        mockMvc.perform(get("/event-applicant-form"))
                .andExpect(status().isOk())  // Expecting status 200
                .andExpect(view().name("eventApplicantForm"));  // Expecting the view name
    }

    @Test
    @WithMockUser(username = "user", roles = {"USER"})  // Simulate a logged-in user
    void testSubmitFormValid() throws Exception {
        mockMvc.perform(post("/event-applicant-form")
                        .flashAttr("eventApplicantForm", validForm))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/applicants/all-event-form"));

        verify(repository, times(1)).save(Mockito.any(EventApplicantForm.class));
    }

    @Test
    @WithMockUser(username = "testUser", roles = "USER")
    void testSubmitFormInvalid() throws Exception {
        mockMvc.perform(post("/event-applicant-form")
                        .param("name", "")
                        .param("email", "invalid-email"))
                .andExpect(status().isOk())  // Expecting 200 because the user is authenticated
                .andExpect(view().name("eventApplicantForm"));  // Corrected view name
    }



    @Test
    @WithMockUser(username = "user", roles = {"USER"})  // Simulating an authenticated user
    void testShowAllApplicants() throws Exception {
        // Mock the service to return a list of applicants
        List<EventApplicantForm> applicants = new ArrayList<>();
        applicants.add(new EventApplicantForm());  // Add an example form to the list
        when(service.getAllApplicants()).thenReturn(applicants);

        mockMvc.perform(get("/applicants/all-event-form"))
                .andExpect(status().isOk())  // Expecting status 200
                .andExpect(model().attributeExists("applicants"))  // Model should contain the applicants
                .andExpect(view().name("showEventApplicants"));  // Expecting the view name
    }

    // Service Tests
    @Test
    void testGetAllApplicants() {
        // Prepare the test data
        List<EventApplicantForm> applicants = new ArrayList<>();
        EventApplicantForm validForm = new EventApplicantForm();
        validForm.setName("John Doe");
        validForm.setEmail("john.doe@example.com");
        validForm.setInterest("Technology");
        validForm.setSpokeTo("Jane Smith");
        validForm.setEventAttended("Tech Summit 2025");
        applicants.add(validForm);  // Add the applicant to the list

        // Mock the repository call to return the list of applicants
        when(repository.findAll()).thenReturn(applicants);

        // Call the service method
        List<EventApplicantForm> result = service.getAllApplicants();

        // Debugging: Print the result to check if it contains data
        System.out.println("Result size: " + result.size());
        System.out.println("First applicant: " + (result.isEmpty() ? "None" : result.get(0).getName()));

        // Assert that the result is not empty
        assertThat(result).isNotEmpty();
        // Assert that the first applicant's name is "John Doe"
        assertThat(result.get(0).getName()).isEqualTo("John Doe");
        // Assert that the list contains exactly one applicant
        assertThat(result.size()).isEqualTo(1);

        // Verify that the repository's findAll method was called
        verify(repository, times(1)).findAll();
    }



    // Repository Tests
    @Test
    void testRepositorySaveAndFind() {
        when(repository.save(validForm)).thenReturn(validForm);
        when(repository.findById(1L)).thenReturn(java.util.Optional.of(validForm));

        EventApplicantForm savedForm = repository.save(validForm);
        assertThat(savedForm).isNotNull();
        assertThat(savedForm.getName()).isEqualTo("John Doe");

        EventApplicantForm foundForm = repository.findById(1L).orElse(null);
        assertThat(foundForm).isNotNull();
        assertThat(foundForm.getEmail()).isEqualTo("john.doe@example.com");
    }

    @BeforeEach
    public void setUp() {
        // Initialize the Validator
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void testValidApplicantForm() {
        // Create a valid EventApplicantForm
        EventApplicantForm form = new EventApplicantForm();
        form.setName("John Doe");
        form.setEmail("john.doe@example.com");
        form.setInterest("Technology");
        form.setSpokeTo("Jane Smith");
        form.setEventAttended("Tech Summit 2025");

        // Validate the form
        Set<jakarta.validation.ConstraintViolation<EventApplicantForm>> violations = validator.validate(form);

        // Assert that no violations occur
        assertTrue(violations.isEmpty(), "There should be no validation errors.");
    }
}
