package org.example.groupproject.applicant.report;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;
import java.util.Map;

@Controller
public class ReportController {
    @Autowired
    private ReportService reportService;

    @GetMapping("/reports")
    public ModelAndView reports() {
        ModelAndView modelAndView = new ModelAndView("reports/reports");
        // Getting data for reports page
        Map<String, Integer> eventData = reportService.getEventData();
        Map<String, Integer> locationData = reportService.getApplicantsByLocation();
        Map<String, Integer> dateData = reportService.getApplicantsByMonth();
        Map<String, Integer> typeData = reportService.getApplicantsByType();

        // Adding data to the model and view
        modelAndView.addObject("eventData", eventData);
        modelAndView.addObject("locationData", locationData);
        modelAndView.addObject("dateData", dateData);
        modelAndView.addObject("typeData", typeData);
        return modelAndView;
    }
}