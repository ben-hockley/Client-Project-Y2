package org.example.groupproject.applicant;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

@Controller
public class ReportController {

    @GetMapping("/reports")
    public ModelAndView reports() {
        ModelAndView modelAndView = new ModelAndView("reports/reports");
        // Temp dummy data
        Map<String, Integer> eventData = Map.of(
                "Event A", 40,
                "Event B", 30,
                "Event C", 20,
                "Event D", 10
        );
        modelAndView.addObject("eventData", eventData);
        return modelAndView;
    }
}