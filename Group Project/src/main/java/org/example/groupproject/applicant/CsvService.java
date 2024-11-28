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
            List<Applicant> applicants = new ArrayList<>();
            for (String[] record : records) {
                if (record.length < 10) {
                    // Skip records that do not have the expected number of columns
                    continue;
                }
                Applicant applicant = new Applicant();
                applicant.setName(record[0]);
                applicant.setEmail(record[1]);
                applicant.setPhone(record[2]);
                applicant.setLocation(record[3]);
                applicant.setCurrentJobRole(record[4]);
                applicant.setOldJobRole(record[5]);
                applicant.setEventID(Integer.parseInt(record[6]));
                applicant.setInternal(Boolean.parseBoolean(record[7]));
                applicant.setStartDate(Date.valueOf(record[8]));
                applicant.setCvFilePath(record[9]);
                applicants.add(applicant);
            }
            csvRepo.saveAll(applicants); // Save all applicants at once
        }
    }
}
