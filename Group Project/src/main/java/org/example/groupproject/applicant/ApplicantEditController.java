package org.example.groupproject.applicant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class ApplicantEditController {

    @Autowired
    private ApplicantRepository applicantRepository;

    @GetMapping("/deleteApplicant/{id}")
    public String deleteApplicant(@PathVariable Integer id) {
        applicantRepository.deleteById(id);
        return "redirect:/applicants/all";
    }

}
