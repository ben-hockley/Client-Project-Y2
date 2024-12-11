package org.example.groupproject.applicant;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileReader;
import java.io.IOException;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@Service
public class CsvService {

    @Autowired
    private CsvRepo csvRepo;

    public void importApplicantsFromCsv(String filePath) throws IOException, CsvException {
        try (CSVReader reader = new CSVReader(new FileReader(filePath))) {
            List<String[]> records = reader.readAll();
            List<ApplicantCsv> applicants = new ArrayList<>();
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            for (String[] record : records) {
                if (record.length < 10) {
                    // Skip records that do not have the expected number of columns
                    continue;
                }
                ApplicantCsv applicantCsv = new ApplicantCsv();
                applicantCsv.setName(record[0]);
                applicantCsv.setEmail(record[1]);
                applicantCsv.setPhone(record[2]);
                applicantCsv.setLocation(record[3]);
                applicantCsv.setCurrentJobRole(record[4]);
                applicantCsv.setOldJobRole(record[5]);
                applicantCsv.setSkills(record[6]);
                applicantCsv.setExpectedSalary(Integer.parseInt(record[7].replace(",", "")));
                applicantCsv.setQualification(record[8]);
                applicantCsv.setEventID(Integer.parseInt(record[9]));
                applicantCsv.setInternal(Boolean.parseBoolean(record[10]));
                try {
                    // Parse the date string to java.sql.Date
                    java.util.Date parsedDate = dateFormat.parse(record[11]);
                    applicantCsv.setStartDate(new java.sql.Date(parsedDate.getTime()));
                } catch (ParseException e) {
                    // Handle the exception if the date cannot be parsed
                    throw new IllegalArgumentException("Invalid date format: " + record[11], e);
                }
                applicantCsv.setCvFilePath(record[12]);
                applicantCsv.setFavourite(false);
                applicants.add(applicantCsv);
            }
            csvRepo.saveAll(applicants); // Save all applicants at once
        }
    }
}
