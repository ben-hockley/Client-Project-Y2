package org.example.groupproject.Full;

import com.opencsv.exceptions.CsvException;
import org.example.groupproject.applicant.CsvController;
import org.example.groupproject.applicant.CsvService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doThrow;

public class CsvControllerTest {

    @Mock
    private CsvService csvService;

    @InjectMocks
    private CsvController csvController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testImportCsv() {
        ModelAndView modelAndView = csvController.importCsv();
        assertEquals("applicant/applicantCsv", modelAndView.getViewName());
    }

    @Test
    public void testImportCsvFile_Success() throws IOException, CsvException {
        MockMultipartFile file = new MockMultipartFile("file", "test.csv", "text/csv", "name,email,phone".getBytes());
        ResponseEntity<String> response = csvController.importCsv(file);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("CSV import successful", response.getBody());
    }

    @Test
    public void testImportCsvFile_EmptyFile() {
        MockMultipartFile file = new MockMultipartFile("file", "test.csv", "text/csv", new byte[0]);
        ResponseEntity<String> response = csvController.importCsv(file);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Please select a CSV file to upload.", response.getBody());
    }


}