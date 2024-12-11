package org.example.groupproject.Full;

import org.example.groupproject.applicant.report.ReportController;
import org.example.groupproject.applicant.report.ReportService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
public class ReportTests {

    @Mock
    private ReportService reportService;

    @InjectMocks
    private ReportController reportController;

    private MockMvc mockMvc;

    @Test
    public void testReportsPage() throws Exception {
        // Given the report service returns mock data
        mockMvc = MockMvcBuilders.standaloneSetup(reportController).build();
        Map<String, Integer> eventData = new HashMap<>();
        eventData.put("Event 1", 10);
        Map<String, Integer> locationData = new HashMap<>();
        locationData.put("Location 1", 5);
        Map<String, Integer> dateData = new HashMap<>();
        dateData.put("Jan 2023", 3);
        Map<String, Integer> typeData = new HashMap<>();
        typeData.put("Internal", 7);

        given(reportService.getEventData()).willReturn(eventData);
        given(reportService.getApplicantsByLocation()).willReturn(locationData);
        given(reportService.getApplicantsByMonth()).willReturn(dateData);
        given(reportService.getApplicantsByType()).willReturn(typeData);

        // When the reports page is accessed
        MvcResult result = mockMvc.perform(get("/reports"))
                .andExpect(status().isOk())
                .andReturn();

        // Then the reports page is correctly displayed with the expected data
        ModelAndView modelAndView = Objects.requireNonNull(result.getModelAndView(), "ModelAndView should not be null");
        assertEquals("reports/reports", modelAndView.getViewName(), "The view name should be reports/reports");
        assertEquals(eventData, modelAndView.getModel().get("eventData"), "The event data should match the mocked data");
        assertEquals(locationData, modelAndView.getModel().get("locationData"), "The location data should match the mocked data");
        assertEquals(dateData, modelAndView.getModel().get("dateData"), "The date data should match the mocked data");
        assertEquals(typeData, modelAndView.getModel().get("typeData"), "The type data should match the mocked data");
    }

    @Test
    public void testGetEventData() {
        // Given the report service returns mock event data
        Map<String, Integer> eventData = new HashMap<>();
        eventData.put("Event 1", 10);
        given(reportService.getEventData()).willReturn(eventData);

        // When retrieving event data
        Map<String, Integer> result = reportService.getEventData();

        // Then the event data should match the mocked data
        assertEquals(eventData, result, "The event data should match the mocked data");
    }

    @Test
    public void testGetApplicantsByLocation() {
        // Given the report service returns mock location data
        Map<String, Integer> locationData = new HashMap<>();
        locationData.put("Location 1", 5);
        given(reportService.getApplicantsByLocation()).willReturn(locationData);

        // When retrieving applicants by location
        Map<String, Integer> result = reportService.getApplicantsByLocation();

        // Then the location data should match the mocked data
        assertEquals(locationData, result, "The location data should match the mocked data");
    }

    @Test
    public void testGetApplicantsByMonth() {
        // Given the report service returns mock date data
        Map<String, Integer> dateData = new HashMap<>();
        dateData.put("Jan 2023", 3);
        given(reportService.getApplicantsByMonth()).willReturn(dateData);

        // When retrieving applicants by month
        Map<String, Integer> result = reportService.getApplicantsByMonth();

        // Then the date data should match the mocked data
        assertEquals(dateData, result, "The date data should match the mocked data");
    }

    @Test
    public void testGetApplicantsByType() {
        // Given the report service returns mock type data
        Map<String, Integer> typeData = new HashMap<>();
        typeData.put("Internal", 7);
        given(reportService.getApplicantsByType()).willReturn(typeData);

        // When retrieving applicants by type
        Map<String, Integer> result = reportService.getApplicantsByType();

        // Then the type data should match the mocked data
        assertEquals(typeData, result, "The type data should match the mocked data");
    }
}