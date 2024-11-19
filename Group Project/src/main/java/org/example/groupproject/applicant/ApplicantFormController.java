package org.example.groupproject.applicant;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.example.groupproject.event.Event;
import org.example.groupproject.event.EventService;

import java.util.List;

@Controller
public class ApplicantFormController {

    private final ApplicantFormService applicantFormService;
    private final EventService eventService;

    @Autowired
    public ApplicantFormController(ApplicantFormService applicantFormService, EventService eventService) {
        this.applicantFormService = applicantFormService;
        this.eventService = eventService;
    }

    @GetMapping({"/applicantForm"})
    public ModelAndView addMenuItemTh() {
        ModelAndView modelAndView = new ModelAndView("applicant/applicantForm");
        ApplicantForm emptyApplicantForm = new ApplicantForm();
        List<Event> events = eventService.getAllEvents();
        modelAndView.addObject("applicantForm", emptyApplicantForm);
        modelAndView.addObject("events", events);
        return modelAndView;
    }

    @PostMapping("/applicantForm")
    public ModelAndView processApplicantForm(@ModelAttribute ApplicantForm applicantForm) {
        applicantFormService.saveApplicantForm(applicantForm);
        return new ModelAndView("redirect:/applicantForm");
    }

}
