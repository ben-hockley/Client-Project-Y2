package org.example.groupproject.applicant;

import com.opencsv.exceptions.CsvException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@RestController
public class CsvController {

    @Autowired
    private CsvService csvService;

    @GetMapping({"/importcsv"})
    public ModelAndView importCsv() {
        ModelAndView modelAndView = new ModelAndView("applicant/applicantCsv");
        return modelAndView;
    }

    @PostMapping("/import-csv")
    public ResponseEntity<String> importCsv(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body("Please select a CSV file to upload.");
        }

        try {
            // Save the file locally
            Path tempDir = Files.createTempDirectory("");
            Path tempFile = tempDir.resolve(file.getOriginalFilename());
            file.transferTo(tempFile.toFile());

            // Import the CSV data
            csvService.importApplicantsFromCsv(String.valueOf(tempFile));

            // Clean up the temporary file
            Files.delete(tempFile);

            return ResponseEntity.ok("CSV import successful");
        } catch (IOException | CsvException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error during CSV import: " + e.getMessage());
        }
    }
}