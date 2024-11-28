package org.example.groupproject.applicant;

import org.example.groupproject.user.User;
import org.example.groupproject.user.UserRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@RequestMapping("/applicants")
@RestController
public class ApplicantController {

    private final ApplicantRepository applicantRepository;

    public ApplicantController(ApplicantRepository applicantRepository, UserRepository userRepository) {
        this.applicantRepository = applicantRepository;
    }

    @GetMapping("/profile/{id}")
    public ModelAndView viewProfile(@PathVariable Integer id){

        Applicant applicant = applicantRepository.findById(id);

        ModelAndView modelAndView = new ModelAndView("applicantProfile"); // templates/gameDetails.html
        modelAndView.addObject("applicant", applicant);
        return modelAndView;
    }

    @GetMapping("/all")
    public ModelAndView viewAllApplicants(User activeUser) {
        ModelAndView modelAndView = new ModelAndView("applicantList");

        List<Applicant> applicants = applicantRepository.findAll();

        modelAndView.addObject("applicants", applicants);
        modelAndView.addObject("activeUser", activeUser);
        return modelAndView;
    }

}
