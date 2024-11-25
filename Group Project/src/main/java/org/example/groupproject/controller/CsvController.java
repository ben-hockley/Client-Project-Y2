package org.example.groupproject.controller;

import com.opencsv.exceptions.CsvException;
import org.example.groupproject.CsvService.CsvService;
import org.springframework.beans.factory.annotation.Autowired;
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
    public String importCsv(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return "Please select a CSV file to upload.";
        }

        try {
            // Save the file locally
            Path tempDir = Files.createTempDirectory("");
            Path tempFile = tempDir.resolve(file.getOriginalFilename());
            file.transferTo(tempFile.toFile());

            // Import the CSV data
            csvService.importApplicantsFromCsv(tempFile.toString());

            // Clean up the temporary file
            Files.delete(tempFile);

            return "CSV import successful";
        } catch (IOException | CsvException e) {
            return "Error during CSV import: " + e.getMessage();
        }
    }
}