package org.example.groupproject.applicant;


import java.time.LocalDate;

public record Applicant  (Long id,
                          String name,
                          String email,
                          String phone,
                          Location location,
                          String currentJobRole,
                          String oldJobRole,
                          String skills,
                            Integer expectedSalary,
                            String qualification,
                          Integer eventId,
                          Boolean isInternal,
                          LocalDate startDate,
                          String cvFilePath,
                          Boolean isFavourite){

    public String getExpiryDateAsString() {
        return startDate.plusYears(1).toString();
    }
}
