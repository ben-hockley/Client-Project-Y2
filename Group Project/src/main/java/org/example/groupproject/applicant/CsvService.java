package org.example.groupproject.applicant;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileReader;
import java.io.IOException;
import java.sql.Date;
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
                applicantCsv.setEventID(Integer.parseInt(record[6]));
                applicantCsv.setInternal(Boolean.parseBoolean(record[7]));
                applicantCsv.setStartDate(Date.valueOf(record[8]));
                applicantCsv.setCvFilePath(record[9]);
                applicants.add(applicantCsv);
            }
            csvRepo.saveAll(applicants); // Save all applicants at once
        }
    }
}
