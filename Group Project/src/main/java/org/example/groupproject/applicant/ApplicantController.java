package org.example.groupproject.applicant;

import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Optional;

@RequestMapping("/applicants")
@RestController
public class ApplicantController {

    private final ApplicantRepository applicantRepository;

    public ApplicantController(ApplicantRepository applicantRepository) {
        this.applicantRepository = applicantRepository;
    }
    @GetMapping("/all")
    public ModelAndView allApplicants() {
        ModelAndView modelAndView = new ModelAndView("applicantList");

        List<Applicant> applicants = applicantRepository.findAll();

        modelAndView.addObject("applicants", applicants);
        return modelAndView;
    }

    @GetMapping("/profile/{id}")
    public ModelAndView getGame(@PathVariable Integer id){

        Applicant applicant = applicantRepository.findById(id);

        ModelAndView modelAndView = new ModelAndView("applicantProfile"); // templates/gameDetails.html
        modelAndView.addObject("applicant", applicant);
        return modelAndView;
    }

}
