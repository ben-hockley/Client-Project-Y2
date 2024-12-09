package org.example.groupproject.applicant;

import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
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
        return jdbcClient.sql("SELECT id,name,email,phone,location,current_job_role,old_job_role,skills,eventId," +
                        "is_internal, start_date, cv_file_path FROM applicants WHERE id = :id")
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

    public List<Applicant> findWithFilters(String searchQuery, Integer eventId, Boolean isInternal, Location location) {
        StringBuilder sql = new StringBuilder("SELECT * FROM applicants WHERE TRUE");

        if (searchQuery != null && !searchQuery.trim().isEmpty()) {
            sql.append(" AND (LOWER(name) LIKE :searchQuery OR LOWER(old_job_role) LIKE :searchQuery OR LOWER(skills) LIKE :searchQuery)");
        }

        if (eventId != null) {
            sql.append(" AND eventid IN (:eventIds)");
        }
        if (isInternal != null) {
            sql.append(" AND is_internal = :isInternal");
        }
        if (location != null && !location.name().equalsIgnoreCase("All")) {
            sql.append(" AND location = :location");
        }

        var query = jdbcClient.sql(sql.toString());

        if (searchQuery != null && !searchQuery.trim().isEmpty()) {
            query = query.param("searchQuery", "%" + searchQuery.toLowerCase() + "%");
        }

        if (eventId != null) {
            query = query.param("eventIds", eventId);
        }
        if (isInternal != null) {
            query = query.param("isInternal", isInternal);
        }
        if (location != null && !location.name().equalsIgnoreCase("All")) {
            query = query.param("location", location.name());
        }
        return query.query(Applicant.class).list();
    }
}
