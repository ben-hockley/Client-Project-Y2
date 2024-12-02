package org.example.groupproject.applicant;


import java.time.LocalDate;

public record Applicant  (Long id,
                          String name,
                          String email,
                          String phone,
                          Location location,
                          String currentJobRole,
                          String oldJobRole,
                          Integer eventId,
                          Boolean isInternal,
                          LocalDate startDate,
                          String cvFilePath){
}
