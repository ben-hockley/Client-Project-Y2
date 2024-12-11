/*
package org.example.groupproject.applicant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class NavBarController {

    private final EventService eventService;

    @Autowired
    public NavBarController(EventService eventService) {
        this.eventService = eventService;
    }

    @GetMapping("/navbarCsv")
    public ModelAndView showApplicantCsv() {
        return new ModelAndView("applicant/applicantCsv");
    }

    @GetMapping("/navbarForm")
    public ModelAndView showApplicantForm() {
        ModelAndView modelAndView = new ModelAndView("applicant/applicantForm");
        modelAndView.addObject("applicantForm", new ApplicantForm());
        modelAndView.addObject("events", eventService.getAllEvents());
        return modelAndView;
    }

    @GetMapping("/applicants/all")
    public ModelAndView showApplicantList() {
        ModelAndView modelAndView = new ModelAndView("applicantList");
        // Add any necessary model attributes here
        return modelAndView;
    }
}*/
