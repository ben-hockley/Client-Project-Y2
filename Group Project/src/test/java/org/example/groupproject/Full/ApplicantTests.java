package org.example.groupproject.Full;

import org.example.groupproject.applicant.Applicant;
import org.example.groupproject.applicant.ApplicantController;
import org.example.groupproject.applicant.ApplicantRepository;
import org.example.groupproject.applicant.EventService;
import org.example.groupproject.applicant.user.User;
import org.example.groupproject.applicant.user.UserRepository;
import org.example.groupproject.filter.Filter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.Authentication;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
public class ApplicantTests {

    @Mock
    private ApplicantRepository applicantRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private EventService eventService;

    @InjectMocks
    private ApplicantController applicantController;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(applicantController).build();
    }

    @Test
    public void testAllApplicants() throws Exception {
        // Given the applicant repository and event service return mock data
        Filter filter = new Filter();
        Authentication authentication = mock(Authentication.class);
        User user = new User();
        given(authentication.getName()).willReturn("testUser");
        given(userRepository.findByUsername("testUser")).willReturn(user);
        given(applicantRepository.findAll()).willReturn(Collections.emptyList());
        given(eventService.getAllEvents()).willReturn(Collections.emptyList());

        // When the all applicants page is accessed
        MvcResult result = mockMvc.perform(get("/applicants/all")
                        .flashAttr("filter", filter)
                        .principal(authentication))
                .andExpect(status().isOk())
                .andReturn();

        // Then the all applicants page is correctly displayed with the expected data
        ModelAndView modelAndView = result.getModelAndView();
        assertNotNull(modelAndView, "The ModelAndView should not be null");
        assertEquals("applicantList", modelAndView.getViewName(),
                "The view name should be applicantList");
        assertTrue(((List<?>) modelAndView.getModel().get("applicants")).isEmpty(),
                "The applicants list should be empty");
    }

    @Test
    public void testViewProfile() throws Exception {
        // Given the applicant repository returns an applicant
        Authentication authentication = mock(Authentication.class);
        User user = new User();
        Applicant applicant = new Applicant(1L, "Test Name", "test@example.com", "1234567890",
                null, "Current Job", "Old Job", "Skills", 30000, "Msc", 1, true,
                LocalDate.now(), "/cv", false);
        given(authentication.getName()).willReturn("testUser");
        given(userRepository.findByUsername("testUser")).willReturn(user);
        given(applicantRepository.findById(1)).willReturn(applicant);
        given(applicantRepository.getContactHistory(1)).willReturn(Collections.emptyList());

        // When the profile page is accessed
        MvcResult result = mockMvc.perform(get("/applicants/profile/1")
                        .principal(authentication))
                .andExpect(status().isOk())
                .andReturn();

        // Then the profile page is correctly displayed with the expected data
        ModelAndView modelAndView = result.getModelAndView();
        assertNotNull(modelAndView, "The ModelAndView should not be null");
        assertEquals("applicantProfile", modelAndView.getViewName(), "The view name should be applicantProfile");
        assertEquals(applicant, modelAndView.getModel().get("applicant"), "The applicant should match the mocked data");
        assertTrue(((List<?>) modelAndView.getModel().get("contactHistory")).isEmpty(), "The contact history should be empty");
    }

    @Test
    public void testLogContact() throws Exception {
        // Given the user repository returns a user
        Authentication authentication = mock(Authentication.class);
        User user = new User();
        given(authentication.getName()).willReturn("testUser");
        given(userRepository.findByUsername("testUser")).willReturn(user);

        // When logging contact
        MvcResult result = mockMvc.perform(post("/applicants/logContact")
                        .contentType("application/json")
                        .content("{\"applicantId\":\"1\", \"subject\":\"Test Subject\"}")
                        .principal(authentication))
                .andExpect(status().isOk())
                .andReturn();

        // Then the response should be as expected
        assertEquals(200, result.getResponse().getStatus(), "The response status should be 200 OK");
        assertTrue(result.getResponse().getContentAsString().isEmpty(), "The response content should be empty");
    }
}