package org.example.groupproject.applicant;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.example.groupproject.event.Event;
import org.example.groupproject.event.EventService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@Controller
public class ApplicantFormController {

    // Setting the upload directory
    @Value("${file.upload-dir}")
    private String uploadDir;

    // Constants for the file upload - max size: 1Mb and format: PDF
    private static final long maxFileSizeBytes = 1024 * 1024;
    private static final String allowedFileType = "application/pdf";

    private final ApplicantFormService applicantFormService;
    private final EventService eventService;

    @Autowired
    public ApplicantFormController(ApplicantFormService applicantFormService, EventService eventService) {
        this.applicantFormService = applicantFormService;
        this.eventService = eventService;
    }

    // Displaying the applicant form
    @GetMapping({"/applicantForm"})
    public ModelAndView displayApplicantForm() {
        ModelAndView modelAndView = new ModelAndView("applicant/applicantForm");
        ApplicantForm emptyApplicantForm = new ApplicantForm();
        List<Event> events = eventService.getAllEvents();
        modelAndView.addObject("applicantForm", emptyApplicantForm);
        modelAndView.addObject("events", events);
        return modelAndView;
    }

    // Processing the applicant form
    @PostMapping("/applicantForm")
    public ModelAndView processApplicantForm(@Valid @ModelAttribute ApplicantForm applicantForm,
                                             BindingResult bindingResult) {

        // Ensuring a CV is uploaded
        if (applicantForm.getCv() == null || applicantForm.getCv().isEmpty()) {
            bindingResult.rejectValue("cv", "error.cv", "A CV must be uploaded.");
        }

        // Ensuring the file is less than the maximum size
        if (applicantForm.getCv() != null && !applicantForm.getCv().isEmpty()) {
            if (applicantForm.getCv().getSize() > maxFileSizeBytes) {
                bindingResult.rejectValue("cv", "error.cv",
                        "File size must not exceed " + (maxFileSizeBytes / (1024 * 1024)) + " MB.");
            }

            // Ensuring the file is a PDF
            String contentType = applicantForm.getCv().getContentType();
            if (!allowedFileType.equals(contentType)) {
                bindingResult.rejectValue("cv", "error.cv", "Only PDF files are allowed.");
            }
        }

        // If the form has errors, it is returned with the error messages
        if (bindingResult.hasErrors()) {
            ModelAndView modelAndView = new ModelAndView("applicant/applicantForm");
            modelAndView.addObject("events", eventService.getAllEvents());
            return modelAndView;
        }

        // Saving the CV
        MultipartFile cv = applicantForm.getCv();
        String cvFilename = UUID.randomUUID() + "-" + cv.getOriginalFilename();
        Path cvPath = Paths.get(uploadDir, cvFilename);
        try {
            Files.createDirectories(cvPath.getParent()); // Ensure directory exists
            Files.write(cvPath, cv.getBytes());
        } catch (IOException e) {
            throw new RuntimeException("Failed to save CV file", e);
        }

        // Save applicant data to the database
        applicantFormService.saveApplicantForm(applicantForm, cvPath.toString());

        // Return success message and reset form
        ModelAndView modelAndView = new ModelAndView("applicant/applicantForm");
        modelAndView.addObject("successMessage", "Form Submitted Successfully");
        modelAndView.addObject("applicantForm", new ApplicantForm());
        modelAndView.addObject("events", eventService.getAllEvents());
        return modelAndView;
    }
}
