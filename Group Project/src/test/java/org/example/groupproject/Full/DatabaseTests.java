//package org.example.groupproject.Full;
//
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.jdbc.core.JdbcTemplate;
//import org.springframework.test.annotation.DirtiesContext;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertTrue;
//
//@SpringBootTest
//@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
//public class DatabaseTests {
//
//    @Autowired
//    private JdbcTemplate jdbcTemplate;
//
//    @Test
//    public void testApplicantsTableExists() {
//        //Given
//        String sql = "SELECT COUNT(*) FROM applicants";
//        //When
//        int count = jdbcTemplate.queryForObject(sql, Integer.class);
//        //Then
//        assertTrue(count > 0);
//    }
//
//
//    @Test
//    public void testEventsTableExists() {
//        //Given
//        String sql = "SELECT COUNT(*) FROM events";
//        //When
//        int count = jdbcTemplate.queryForObject(sql, Integer.class);
//        //Then
//        assertTrue(count > 0);
//    }
//
//    @Test
//    public void testUsersTableExists() {
//        //Given
//        String sql = "SELECT COUNT(*) FROM users";
//        //When
//        int count = jdbcTemplate.queryForObject(sql, Integer.class);
//        //Then
//        assertTrue(count > 0);
//    }
//
//    @Test
//    public void testApplicantsTableHasBeenPopulated(){
//        //Given
//        String sql = "SELECT name FROM applicants WHERE id = 1";
//        //When
//        String name = jdbcTemplate.queryForObject(sql, String.class);
//        //Then
//        assertEquals("Test User", name);
//    }
//
//    @Test
//    public void testEventsTableHasBeenPopulated(){
//        //Given
//        String sql = "SELECT event_name FROM events WHERE eventid = 1";
//        //When
//        String name = jdbcTemplate.queryForObject(sql, String.class);
//        //Then
//        assertEquals("Test Event 1", name);
//    }
//
//    @Test
//    public void testUsersTableHasBeenPopulated(){
//        //Given
//        String sql = "SELECT username FROM users WHERE id = 1";
//        //When
//        String name = jdbcTemplate.queryForObject(sql, String.class);
//        //Then
//        assertEquals("adminTest", name);
//    }
//
//    @Test
//    public void testAddApplicantToApplicantsTable() {
//        //Given
//        String insertSql = "INSERT INTO applicants (name, email, phone, location, current_job_role, old_job_role, skills," +
//                " eventid, is_internal, start_date, cv_file_path,is_favourite) " +
//                "VALUES ('New User', 'newuser@test.com', '0987654321', 'New Location', 'NewJob', " +
//                "'OldJob1, OldJob2', 'Skill1, Skill2', 1, true, '2022-01-01', 'path/to/cv',false)";
//        jdbcTemplate.update(insertSql);
//        //When
//        String verifySql = "SELECT COUNT(*) FROM applicants";
//        int count = jdbcTemplate.queryForObject(verifySql, Integer.class);
//        //Then
//        assertTrue(count > 1);
//    }
//
//    @Test
//    public void testAddEventToEventsTable() {
//        //Given
//        String insertSql = "INSERT INTO events (event_name, event_location) " +
//                "VALUES ('New Event', 'New Location')";
//        jdbcTemplate.update(insertSql);
//
//        //When
//        String verifySql = "SELECT COUNT(*) FROM events";
//        int count = jdbcTemplate.queryForObject(verifySql, Integer.class);
//        //Then
//        assertTrue(count > 1);
//    }
//
//    @Test
//    public void testAddAdminToUsersTable() {
//        //Given
//        String insertSql = "INSERT INTO users (username, email, password, is_admin) " +
//                "VALUES ('New Admin','newadmin@test.com', 'password', true)";
//        jdbcTemplate.update(insertSql);
//
//        //When
//        String verifySql = "SELECT COUNT(*) FROM users";
//        int count = jdbcTemplate.queryForObject(verifySql, Integer.class);
//        //Then
//        assertTrue(count > 1);
//    }
//
//    @Test
//    public void testGetEventFromAppicantsTable() {
//        //Given
//        String sql1 = "SELECT eventid FROM applicants WHERE id = 1";
//        String appEventId = jdbcTemplate.queryForObject(sql1, String.class);
//        //When
//        String sql2 = "SELECT event_name FROM events WHERE eventid =" + appEventId;
//        String name = jdbcTemplate.queryForObject(sql2, String.class);
//        //Then
//        assertEquals("Test Event 1", name);
//    }
//}
