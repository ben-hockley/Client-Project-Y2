package org.example.groupproject.applicant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@Controller
public class EventApplicantFormController {

    @Autowired
    private EventApplicantFormRepository repository;

    //Displays Form
    @GetMapping("/event-applicant-form")
    public String showForm(Model model) {
        model.addAttribute("eventApplicantForm", new EventApplicantForm());
        return "eventApplicantForm";
    }

    @PostMapping("/event-applicant-form")
    public String submitForm(@Valid @ModelAttribute EventApplicantForm eventApplicantForm, BindingResult result, Model model) {
        if (result.hasErrors()) {
            // Return to the form if there are validation errors
            return "eventApplicantForm";
        }

        repository.save(eventApplicantForm);  // Save form data to the database
        return "redirect:/applicants/all-event-form";
    }

    @GetMapping("/applicants/all-event-form")
    public String showAllApplicants(Model model) {
        List<EventApplicantForm> applicants = repository.findAll();
        model.addAttribute("applicants", applicants);
        return "showEventApplicants";
    }


}
