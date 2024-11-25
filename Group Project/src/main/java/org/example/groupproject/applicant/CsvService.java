package org.example.groupproject.applicant;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileReader;
import java.io.IOException;
import java.util.List;

@Service
public class CsvService {

    @Autowired
    private CsvRepo csvRepo;

    public void importApplicantsFromCsv(String filePath) throws IOException, CsvException {
        try (CSVReader reader = new CSVReader(new FileReader(filePath))) {
            List<String[]> records = reader.readAll();
            for (String[] record : records) {
                Applicant applicant = new Applicant();
                applicant.setName(String.valueOf(record[0]));
                applicant.setEmail(record[1]);
                applicant.setPhone(record[2]);
                applicant.setLocation(record[3]);
                applicant.setCurrentJobRole(record[4]);
                applicant.setOldJobRole(record[5]);
                applicant.setEventID(Integer.parseInt(record[6]));
                applicant.setInternal(Boolean.parseBoolean(record[7]));
                applicant.setStartDate(java.sql.Date.valueOf(record[8]));
                applicant.setCvFilePath(record[9]);
                csvRepo.save(applicant);
            }
        }
    }
}
