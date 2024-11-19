package org.example.groupproject.applicant;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ApplicantController {
    @GetMapping("/applicantForm")
    public ModelAndView getApplicantForm() {
        ModelAndView modelAndView = new ModelAndView("applicant/applicantForm");
        modelAndView.addObject("applicant");
        return modelAndView;
    }
}
