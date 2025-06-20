package org.example.groupproject.applicant;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class ApplicantFormService {

    private final JdbcTemplate jdbcTemplate;

    public ApplicantFormService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // Saving the applicant form to the database
    public void saveApplicantForm(ApplicantForm applicantForm, String cvFilePath) {
        String sql = "INSERT INTO applicants (name, email, phone, location, current_job_role, old_job_role,skills,expected_salary,qualification, " +
                "eventID, is_internal, start_date, cv_file_path, is_favourite) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql,
                applicantForm.getName(),
                applicantForm.getEmail(),
                applicantForm.getPhone(),
                applicantForm.getLocation().name(),
                applicantForm.getMostRecentJob(),
                applicantForm.getVacancyAppliedFor(),
                applicantForm.getRelevantSkills(),
                applicantForm.getExpectedSalary(),
                applicantForm.getQualification(),
                applicantForm.getEvent() != null ? Integer.valueOf(applicantForm.getEvent()) : null,
                applicantForm.getIsInternal(),
                LocalDate.now(),
                cvFilePath,
                false //set applicant as not favourite by default
                );

        if (applicantForm.isNewsLetterSub()) {
            String newsletterSql = "INSERT INTO newsletter (email) VALUES (?)"; // Use 'email' column
            jdbcTemplate.update(newsletterSql, applicantForm.getEmail());
        }

    }

    public Applicant findApplicantById(Long id) {
        String sql = "SELECT id, name, email, phone, location, current_job_role, old_job_role,skills,expected_salary,qualification, eventid, " +
                "is_internal, start_date, cv_file_path, is_favourite FROM applicants WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, (rs, rowNum) ->
                new Applicant(
                        rs.getLong("id"),
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getString("phone"),
                        Location.valueOf(rs.getString("location")),
                        rs.getString("current_job_role"),
                        rs.getString("old_job_role"),
                        rs.getString("skills"),
                        rs.getInt("expected_salary"),
                        rs.getString("qualification"),
                        rs.getInt("eventid"),
                        rs.getBoolean("is_internal"),
                        rs.getDate("start_date").toLocalDate(),
                        rs.getString("cv_file_path"),
                        rs.getBoolean("is_favourite")
                ));
    }
    public void updateApplicantCvPath(Long applicantId, String cvFilePath) {
        String sql = "UPDATE applicants SET cv_file_path = ? WHERE id = ?";
        jdbcTemplate.update(sql, cvFilePath, applicantId);
    }


}
