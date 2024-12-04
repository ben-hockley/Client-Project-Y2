package org.example.groupproject.applicant;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.example.groupproject.applicant.Applicant;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Controller
@RequestMapping("/cv")
public class CvController {
    private final CvRepository cvRepository;
    private final ApplicantFormService applicantFormService;

    @Value("${file.upload-dir}")
    private String uploadDir;
    private static final long maxFileSizeBytes = 1024 * 1024;
    private static final String allowedFileType = "application/pdf";

    public CvController(CvRepository cvRepository, ApplicantFormService applicantFormService) {
        this.cvRepository = cvRepository;
        this.applicantFormService = applicantFormService;
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> serveCv(@PathVariable Integer id) {
        Applicant applicant = cvRepository.findById(id);
        if (applicant == null || applicant.cvFilePath() == null || applicant.cvFilePath().isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Applicant does not have a CV uploaded.");
        }
        File file = new File(applicant.cvFilePath());
        if (!file.exists()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Applicant's CV cannot be found.");
        }
        try {
            byte[] fileContent = Files.readAllBytes(file.toPath());
            String contentType = Files.probeContentType(file.toPath());

            return ResponseEntity.ok()
                    .header("Content-Type", contentType)
                    .header("Content-Disposition", "inline; filename=\"" + file.getName() + "\"")
                    .body(fileContent);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error reading CV file.");
        }
    }
    // Redirect to the applicant's profile page after uploading the CV
    // Learn more about redirecting in with SpringBoot using the following website:
    // https://www.baeldung.com/spring-redirect-and-forward
    @PostMapping("/upload")
    public String uploadCv(@RequestParam("applicantId") Long applicantId,
                           @RequestParam("cv") MultipartFile cv,
                           RedirectAttributes redirectAttributes) {

        if (cv == null || cv.isEmpty()) {
            redirectAttributes.addFlashAttribute("errorMessage", "A CV must be uploaded.");
            return "redirect:/applicants/profile/" + applicantId;
        }
        if (cv.getSize() > maxFileSizeBytes) {
            redirectAttributes.addFlashAttribute("errorMessage",
                    "File size must not exceed " + (maxFileSizeBytes / (1024 * 1024)) + " MB.");
            return "redirect:/applicants/profile/" + applicantId;
        }
        if (!allowedFileType.equals(cv.getContentType())) {
            redirectAttributes.addFlashAttribute("errorMessage", "Only PDF files are allowed.");
            return "redirect:/applicants/profile/" + applicantId;
        }
        String cvFilename = UUID.randomUUID() + "-" + cv.getOriginalFilename();
        Path cvPath = Paths.get(uploadDir, cvFilename);
        try {
            Files.createDirectories(cvPath.getParent());
            Files.write(cvPath, cv.getBytes());
        } catch (IOException e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Failed to save CV file.");
            return "redirect:/applicants/profile/" + applicantId;
        }

        applicantFormService.updateApplicantCvPath(applicantId, cvPath.toString());

        redirectAttributes.addFlashAttribute("successMessage", "CV uploaded successfully.");
        return "redirect:/applicants/profile/" + applicantId;
    }
}
