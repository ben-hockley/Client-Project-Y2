package org.example.groupproject.Full;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.DirtiesContext;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class DatabaseTests {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    public void testApplicantsTableExists() {
        String sql = "SELECT COUNT(*) FROM applicants";
        int count = jdbcTemplate.queryForObject(sql, Integer.class);
        assertTrue(count > 0);
    }

    @Test
    public void testEventsTableExists() {
        String sql = "SELECT COUNT(*) FROM events";
        int count = jdbcTemplate.queryForObject(sql, Integer.class);
        assertTrue(count > 0);
    }

    @Test
    public void testAdminTableExists() {
        String sql = "SELECT COUNT(*) FROM admin";
        int count = jdbcTemplate.queryForObject(sql, Integer.class);
        assertTrue(count > 0);
    }

    @Test
    public void testApplicantsTableHasBeenPopulated(){
        String sql = "SELECT name FROM applicants WHERE id = 1";
        String name = jdbcTemplate.queryForObject(sql, String.class);
        assertTrue("Test User".equals(name));
    }

    @Test
    public void testEventsTableHasBeenPopulated(){
        String sql = "SELECT eventName FROM events WHERE eventId = 1";
        String name = jdbcTemplate.queryForObject(sql, String.class);
        assertTrue("Test Event".equals(name));
    }

    @Test
    public void testAdminTableHasBeenPopulated(){
        String sql = "SELECT adminUserName FROM admin WHERE adminId = 1";
        String name = jdbcTemplate.queryForObject(sql, String.class);
        assertTrue("adminTest".equals(name));
    }

    @Test
    public void testAddApplicantToApplicantsTable() {
        // Insert a new applicant
        String insertSql = "INSERT INTO applicants (name, email, phone, location, currentJobRole, oldJobRole, eventID, isInternal, startDate) " +
                "VALUES ('New User', 'newuser@test.com', '0987654321', 'New Location', 'NewJob', 'OldJob1, OldJob2', 1, true, '2022-01-01')";
        jdbcTemplate.update(insertSql);

        // Verify the insertion
        String verifySql = "SELECT COUNT(*) FROM applicants";
        int count = jdbcTemplate.queryForObject(verifySql, Integer.class);
        System.out.println(count);
        assertTrue(count > 1);
    }

    @Test
    public void testAddEventToEventsTable() {
        // Insert a new applicant
        String insertSql = "INSERT INTO events (eventName, eventLocation) " +
                "VALUES ('New Event', 'New Location')";
        jdbcTemplate.update(insertSql);

        // Verify the insertion
        String verifySql = "SELECT COUNT(*) FROM events";
        int count = jdbcTemplate.queryForObject(verifySql, Integer.class);
        System.out.println(count);
        assertTrue(count > 1);
    }

    @Test
    public void testAddAdminToAdminsTable() {
        // Insert a new applicant
        String insertSql = "INSERT INTO admin (adminUserName, adminEmail, adminPassword, isAdmin) " +
                "VALUES ('New Admin','newadmin@test.com', 'password', true)";
        jdbcTemplate.update(insertSql);

        // Verify the insertion
        String verifySql = "SELECT COUNT(*) FROM admin";
        int count = jdbcTemplate.queryForObject(verifySql, Integer.class);
        System.out.println(count);
        assertTrue(count > 1);
    }
}
