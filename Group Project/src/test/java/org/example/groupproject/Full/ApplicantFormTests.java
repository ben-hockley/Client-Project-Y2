package org.example.groupproject.Full;

import org.example.groupproject.applicant.Event;
import org.example.groupproject.applicant.EventService;
import org.example.groupproject.applicant.ApplicantForm;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.validation.BindingResult;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

@SpringBootTest
@AutoConfigureMockMvc
public class ApplicantFormTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EventService eventService;

    @Test
    @WithMockUser
    public void testDisplayApplicantForm() throws Exception {
        // Given an event service to return an empty list of events
        List<Event> events = Collections.emptyList();
        given(eventService.getAllEvents()).willReturn(events);

        // When the form page is accessed
        MvcResult result = mockMvc.perform(get("/applicantForm"))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        // Then the applicant form is correctly displayed
        assertEquals("applicant/applicantForm", Objects.requireNonNull(result.getModelAndView()).getViewName(),
                "The view name should be applicant/applicantForm");
        assertTrue(result.getModelAndView().getModel().containsKey("applicantForm"),
                "The model should contain applicantForm");
        assertEquals(events, result.getModelAndView().getModel().get("events"),
                "The events list should match the mocked data");
    }

    @Test
    @WithMockUser
    public void testProcessApplicantForm_FileTooLarge() throws Exception {
        // Given a CV file exceeding the size limit is uploaded
        MultipartFile mockFile = mock(MultipartFile.class);
        given(mockFile.isEmpty()).willReturn(false);
        given(mockFile.getSize()).willReturn(2 * 1024 * 1024L);
        given(mockFile.getContentType()).willReturn("application/pdf");

        ApplicantForm applicantForm = new ApplicantForm();
        applicantForm.setCv(mockFile);

        // When the form is submitted
        MvcResult result = mockMvc.perform(post("/applicantForm")
                        .flashAttr("applicantForm", applicantForm)
                        .with(csrf()))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        // Then the model contains field errors for the CV
        var bindingResult = (BindingResult) Objects.requireNonNull(result.getModelAndView()).getModel()
                .get("org.springframework.validation.BindingResult.applicantForm");
        assertTrue(bindingResult.hasFieldErrors("cv"),
                "The CV field should have validation errors due to size");
    }

    @Test
    @WithMockUser
    public void testProcessApplicantForm_InvalidFileType() throws Exception {
        // Given a CV file with an invalid file type is uploaded
        MultipartFile mockFile = mock(MultipartFile.class);
        given(mockFile.isEmpty()).willReturn(false);
        given(mockFile.getSize()).willReturn(500L);
        given(mockFile.getContentType()).willReturn("image/png");

        ApplicantForm applicantForm = new ApplicantForm();
        applicantForm.setCv(mockFile);

        // When the form is submitted
        MvcResult result = mockMvc.perform(post("/applicantForm")
                        .flashAttr("applicantForm", applicantForm)
                        .with(csrf()))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        // Then: Verify the model contains field errors for the CV
        var bindingResult = (BindingResult) Objects.requireNonNull(result.getModelAndView()).getModel()
                .get("org.springframework.validation.BindingResult.applicantForm");
        assertTrue(bindingResult.hasFieldErrors("cv"),
                "The CV field should have validation errors due to file type");
    }

    @Test
    @WithMockUser
    public void testProcessApplicantForm_MissingRequiredFields() throws Exception {
        // Given an applicant form with missing required fields is submitted
        ApplicantForm applicantForm = new ApplicantForm();
        applicantForm.setName(""); // Missing name
        applicantForm.setEmail(""); // Missing email
        applicantForm.setPhone(""); // Missing phone

        // When the form is submitted
        MvcResult result = mockMvc.perform(post("/applicantForm")
                        .flashAttr("applicantForm", applicantForm)
                        .with(csrf()))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        // Then the model contains field errors for the missing fields
        var bindingResult = (BindingResult) Objects.requireNonNull(result.getModelAndView()).getModel()
                .get("org.springframework.validation.BindingResult.applicantForm");
        assertTrue(bindingResult.hasFieldErrors("name"), "The name field should have validation errors");
        assertTrue(bindingResult.hasFieldErrors("email"), "The email field should have validation errors");
        assertTrue(bindingResult.hasFieldErrors("phone"), "The phone field should have validation errors");
    }

    @Test
    @WithMockUser
    public void testProcessApplicantForm_InvalidEmailFormat() throws Exception {
        // Given an applicant form with an invalid email format is submitted
        ApplicantForm applicantForm = new ApplicantForm();
        applicantForm.setName("Test Name");
        applicantForm.setEmail("invalid-email"); // Invalid email format
        applicantForm.setPhone("1234567890");

        // When the form is submitted
        MvcResult result = mockMvc.perform(post("/applicantForm")
                        .flashAttr("applicantForm", applicantForm)
                        .with(csrf()))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        // Then the model contains field errors for the email
        var bindingResult = (BindingResult) Objects.requireNonNull(result.getModelAndView()).getModel()
                .get("org.springframework.validation.BindingResult.applicantForm");
        assertTrue(bindingResult.hasFieldErrors("email"), "The email field should have validation errors due to invalid format");
    }
}