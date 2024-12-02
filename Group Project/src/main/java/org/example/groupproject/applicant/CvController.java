package org.example.groupproject.applicant;

import org.springframework.http.HttpStatus;
import org.example.groupproject.applicant.Applicant;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

@Controller
@RequestMapping("/cv")
public class CvController {
    private final CvRepository cvRepository;

    public CvController(CvRepository cvRepository) {
        this.cvRepository = cvRepository;
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
}
