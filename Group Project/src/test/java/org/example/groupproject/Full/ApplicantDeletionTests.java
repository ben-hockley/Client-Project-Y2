package org.example.groupproject.Full;

import org.example.groupproject.applicant.ApplicantRepository;
import org.example.groupproject.applicant.ApplicantScheduler;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.DirtiesContext;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class ApplicantDeletionTests {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private ApplicantRepository applicantRepository;

    @Autowired
    private ApplicantScheduler applicantScheduler;

    @Test
    public void testHasBeenInDatabaseForMoreThanAYear() {
        String insertSql = "INSERT INTO applicants (id, name, email, phone, location, current_job_role, old_job_role, " +
                "skills,expected_salary, qualification, eventid, is_internal, start_date,is_favourite) VALUES (?, ?, ?, ?, ?, ?,?,?, ?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(insertSql, 11L, "Test User", "test@test.com", "1234567890",
                null, "Developer", "Junior Developer", "Skill1, Skill2", 100000, "MSc", 1, true, LocalDate.now().minusYears(2),false);

        String sql1 = "SELECT id FROM applicants WHERE id = 11";
        Integer id = jdbcTemplate.queryForObject(sql1, Integer.class);

        boolean result = applicantRepository.hasBeenInDatabaseForMoreThanAYear(id);
        assertTrue(result);
    }

    @Test
    public void testDeleteApplicantsInDatabaseForMoreThanAYear() {
        String insertSql = "INSERT INTO applicants (id, name, email, phone, location, current_job_role, old_job_role, " +
                "skills, expected_salary, qualification, eventid, is_internal, start_date,is_favourite) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(insertSql, 11L, "Test User", "test@test.com", "1234567890",
                null, "Developer", "Junior Developer", "Skill1, Skill2", 100000, "MSc", 1, true, LocalDate.now().minusYears(2),false);

        String sql1 = "SELECT COUNT(*) FROM applicants WHERE id = 11";
        Integer numOfApplicantsBeforeDelete = jdbcTemplate.queryForObject(sql1, Integer.class);

        applicantRepository.deleteById(11);

        Integer numOfApplicantsAfterDelete = jdbcTemplate.queryForObject(sql1, Integer.class);
        assertTrue(numOfApplicantsAfterDelete < numOfApplicantsBeforeDelete);
    }

    @Test
    public void testScheduler() {
        // Given
        String insertSql = "INSERT INTO applicants (id, name, email, phone, location, current_job_role, old_job_role, " +
                "skills, expected_salary, qualification, eventid, is_internal, start_date,is_favourite) VALUES (?,?,?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(insertSql, 11L, "Test User", "test@test.com", "1234567890",
                null, "Developer", "Junior Developer", "Skill1, Skill2", 100000, "MSc", 1, true, LocalDate.now().minusYears(2),false);

        String sql1 = "SELECT COUNT(*) FROM applicants WHERE id = 11";
        Integer numOfApplicantsBeforeDelete = jdbcTemplate.queryForObject(sql1, Integer.class);

        // When
        applicantScheduler.deleteApplicantsInDatabaseForMoreThanAYear();

        // Then
        Integer numOfApplicantsAfterDelete = jdbcTemplate.queryForObject(sql1, Integer.class);
        assertTrue(numOfApplicantsAfterDelete < numOfApplicantsBeforeDelete);
    }

    @Test
    public void testApplicantHasNotBeenInDatabaseForMoreThanAYear(){
        String insertSql = "INSERT INTO applicants (id, name, email, phone, location, current_job_role, old_job_role, " +
                "skills, expected_salary, qualification, eventid, is_internal, start_date,is_favourite) VALUES (?,?,?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(insertSql, 12L, "New User", "new@test.com", "0987654321",
                null, "Tester", "Intern", "Skill1, Skill2",10000, "MSc", 2, false, LocalDate.now().minusMonths(6),false);

        String sql1 = "SELECT id FROM applicants WHERE id = 12";
        Integer id = jdbcTemplate.queryForObject(sql1, Integer.class);

        boolean result = applicantRepository.hasBeenInDatabaseForMoreThanAYear(id);
        assertTrue(!result);
    }

    @Test
    public void testMultipleApplicantsDeletion() {
        String insertSql = "INSERT INTO applicants (id, name, email, phone, location, current_job_role, old_job_role, " +
                "skills, expected_salary, qualification, eventid, is_internal, start_date,is_favourite) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?,?)";
        jdbcTemplate.update(insertSql, 13L, "User One", "one@test.com", "1111111111",
                null, "Manager", "Assistant Manager", "Skill1, Skill2", 100000, "Msc", 3, true, LocalDate.now().minusYears(2), false);
        jdbcTemplate.update(insertSql, 14L, "User Two", "two@test.com", "2222222222",
                null, "Analyst", "Junior Analyst", "Skill1, Skill2", 100000, "MSc", 4, false, LocalDate.now().minusYears(3),false);

        String sql1 = "SELECT COUNT(*) FROM applicants WHERE id IN (13, 14)";
        Integer numOfApplicantsBeforeDelete = jdbcTemplate.queryForObject(sql1, Integer.class);

        applicantScheduler.deleteApplicantsInDatabaseForMoreThanAYear();

        Integer numOfApplicantsAfterDelete = jdbcTemplate.queryForObject(sql1, Integer.class);
        assertTrue(numOfApplicantsAfterDelete < numOfApplicantsBeforeDelete);
    }

    @Test
    public void testBoundaryConditionForOneYear() {
        String insertSql = "INSERT INTO applicants (id, name, email, phone, location, current_job_role, old_job_role, " +
                "skills, expected_salary, qualification, eventid, is_internal, start_date,is_favourite) VALUES (?,?,?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(insertSql, 15L, "Boundary User", "boundary@test.com", "3333333333",
                null, "Consultant", "Associate Consultant", "Skill1, Skill2",100000,"MSc", 5, true, LocalDate.now().minusYears(1),false);

        String sql1 = "SELECT id FROM applicants WHERE id = 15";
        Integer id = jdbcTemplate.queryForObject(sql1, Integer.class);

        boolean result = applicantRepository.hasBeenInDatabaseForMoreThanAYear(id);
        assertTrue(!result);
    }
}
