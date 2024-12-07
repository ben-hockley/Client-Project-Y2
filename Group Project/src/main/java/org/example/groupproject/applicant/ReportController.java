package org.example.groupproject.applicant;

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
        Map<String, Integer> eventData = reportService.getEventData();
        modelAndView.addObject("eventData", eventData);
        return modelAndView;
    }
}