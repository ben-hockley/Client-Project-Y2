package org.example.groupproject.Full;

import org.example.groupproject.applicant.ApplicantCsv;
import org.junit.jupiter.api.Test;

import java.sql.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ApplicantCsvTest {

    @Test
    public void testGetSetId() {
        ApplicantCsv applicant = new ApplicantCsv();
        applicant.setId(1);
        assertEquals(1, applicant.getId());
    }

    @Test
    public void testGetSetName() {
        ApplicantCsv applicant = new ApplicantCsv();
        applicant.setName("John Doe");
        assertEquals("John Doe", applicant.getName());
    }

    @Test
    public void testGetSetEmail() {
        ApplicantCsv applicant = new ApplicantCsv();
        applicant.setEmail("john@example.com");
        assertEquals("john@example.com", applicant.getEmail());
    }

    @Test
    public void testGetSetPhone() {
        ApplicantCsv applicant = new ApplicantCsv();
        applicant.setPhone("1234567890");
        assertEquals("1234567890", applicant.getPhone());
    }

    @Test
    public void testGetSetLocation() {
        ApplicantCsv applicant = new ApplicantCsv();
        applicant.setLocation("Location");
        assertEquals("Location", applicant.getLocation());
    }

    @Test
    public void testGetSetCurrentJobRole() {
        ApplicantCsv applicant = new ApplicantCsv();
        applicant.setCurrentJobRole("Current Job");
        assertEquals("Current Job", applicant.getCurrentJobRole());
    }

    @Test
    public void testGetSetOldJobRole() {
        ApplicantCsv applicant = new ApplicantCsv();
        applicant.setOldJobRole("Old Job");
        assertEquals("Old Job", applicant.getOldJobRole());
    }

    @Test
    public void testGetSetSkills() {
        ApplicantCsv applicant = new ApplicantCsv();
        applicant.setSkills("Skills");
        assertEquals("Skills", applicant.getSkills());
    }

    @Test
    public void testGetSetExpectedSalary() {
        ApplicantCsv applicant = new ApplicantCsv();
        applicant.setExpectedSalary(50000);
        assertEquals(50000, applicant.getExpectedSalary());
    }

    @Test
    public void testGetSetQualification() {
        ApplicantCsv applicant = new ApplicantCsv();
        applicant.setQualification("Qualification");
        assertEquals("Qualification", applicant.getQualification());
    }

    @Test
    public void testGetSetEventID() {
        ApplicantCsv applicant = new ApplicantCsv();
        applicant.setEventID(1);
        assertEquals(1, applicant.getEventID());
    }

    @Test
    public void testGetSetIsInternal() {
        ApplicantCsv applicant = new ApplicantCsv();
        applicant.setInternal(true);
        assertEquals(true, applicant.isInternal());
    }

    @Test
    public void testGetSetStartDate() {
        ApplicantCsv applicant = new ApplicantCsv();
        Date date = new Date(System.currentTimeMillis());
        applicant.setStartDate(date);
        assertEquals(date, applicant.getStartDate());
    }

    @Test
    public void testGetSetCvFilePath() {
        ApplicantCsv applicant = new ApplicantCsv();
        applicant.setCvFilePath("cv/path");
        assertEquals("cv/path", applicant.getCvFilePath());
    }

    @Test
    public void testGetSetIsFavourite() {
        ApplicantCsv applicant = new ApplicantCsv();
        applicant.setFavourite(true);
        assertEquals(true, applicant.isFavourite());
    }
}