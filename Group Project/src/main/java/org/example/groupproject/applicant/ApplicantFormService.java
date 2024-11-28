package org.example.groupproject.applicant;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class ApplicantFormService {

    private final JdbcTemplate jdbcTemplate;

    public ApplicantFormService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // Saving the applicant form to the database
    public void saveApplicantForm(ApplicantForm applicantForm, String cvFilePath) {
        String sql = "INSERT INTO applicants (name, email, phone, location, current_job_role, old_job_role, eventID," +
                "is_internal, start_date, cv_file_path) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql,
                applicantForm.getName(),
                applicantForm.getEmail(),
                applicantForm.getPhone(),
                applicantForm.getLocation(),
                applicantForm.getMostRecentJob(),
                applicantForm.getVacancyAppliedFor(),
                applicantForm.getEvent() != null ? Integer.valueOf(applicantForm.getEvent()) : null,
                applicantForm.getIsInternal(),
                LocalDate.now(),
                cvFilePath);
    }
}
