package org.example.groupproject.applicant;

import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public class ApplicantRepository {

    private final JdbcClient jdbcClient;
    public ApplicantRepository(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }
    public List<Applicant> findAll() {
        return jdbcClient.sql("SELECT * FROM applicants")
                .query(Applicant.class)
                .list();
    }

    public Applicant findById(Integer id) {
        return jdbcClient.sql("SELECT id,name,email,phone,location,currentJobRole,oldJobRole,eventId,isInternal," +
                        "startDate FROM applicants WHERE id = :id" )
                .param("id", id)
                .query(Applicant.class)
                .single();
    }

    public boolean hasBeenInDatabaseForMoreThanAYear(Integer id) {
        Applicant applicant = findById(id);
        if (applicant != null) {
            LocalDate oneYearAgo = LocalDate.now().minusYears(1);
            return applicant.startDate().isBefore(oneYearAgo);
        }
        return false;
    }

    public void deleteById(Integer id) {
        jdbcClient.sql("DELETE FROM applicants WHERE id = :id")
                .param("id", id)
                .update();
    }
}
