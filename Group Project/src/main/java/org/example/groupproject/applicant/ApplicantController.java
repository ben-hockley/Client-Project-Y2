package org.example.groupproject.applicant;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@RequestMapping("/applicants")
@RestController
public class ApplicantController {

    private final ApplicantRepository applicantRepository = new ApplicantRepository();

    @GetMapping("/all")
    public ModelAndView allApplicants() {
        ModelAndView modelAndView = new ModelAndView("applicantList");

        List<Applicant> applicants = applicantRepository.findAll();

        modelAndView.addObject("applicants", applicants);
        return modelAndView;
    }

}
