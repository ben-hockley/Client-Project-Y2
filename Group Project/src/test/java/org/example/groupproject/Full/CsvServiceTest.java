package org.example.groupproject.Full;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import org.example.groupproject.applicant.CsvRepo;
import org.example.groupproject.applicant.CsvService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class CsvServiceTest {

    @Mock
    private CsvRepo csvRepo;

    @Mock
    private CSVReader csvReader;

    @InjectMocks
    private CsvService csvService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }



    @Test
    public void testImportApplicantsFromCsv_Exception() throws IOException, CsvException {
        when(csvReader.readAll()).thenThrow(new IOException("Error"));

        assertThrows(IOException.class, () -> csvService.importApplicantsFromCsv("test.csv"));
    }
}