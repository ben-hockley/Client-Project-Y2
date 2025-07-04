package org.example.groupproject.applicant;

import org.example.groupproject.applicant.Applicant;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

@Repository
public class CvRepository {
    private final JdbcClient jdbcClient;
    public CvRepository(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }
    public Applicant findById(Integer id) {
        return jdbcClient.sql("SELECT id, name, email, phone, location, current_job_role, old_job_role, skills,expected_salary,qualification, eventid, " +
                        "is_internal, start_date, cv_file_path,is_favourite FROM applicants WHERE id = :id")
                .param("id", id)
                .query(Applicant.class)
                .single();
    }
}
