package org.example.groupproject.applicant.report;

import org.example.groupproject.applicant.Location;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class ReportService {

    private final JdbcTemplate jdbcTemplate;

    public ReportService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Map<String, Integer> getEventData() {
        // Getting event names and IDs
        String sqlEvents = "SELECT eventid AS id, event_name AS name FROM events";
        List<Map<String, Object>> events = jdbcTemplate.queryForList(sqlEvents);

        Map<String, Integer> eventData = new HashMap<>();

        for (Map<String, Object> event : events) {
            Integer eventIdInt = (Integer) event.get("id");
            Long eventId = eventIdInt.longValue();

            String eventName = (String) event.get("name");

            // Counting number of applicants for each event
            String sqlCount = "SELECT COUNT(*) FROM applicants WHERE eventid = ?";
            Integer applicantCount = jdbcTemplate.queryForObject(sqlCount, Integer.class, eventId);

            // Adding the count to the map
            eventData.put(eventName, applicantCount);
        }

        return eventData;
    }

    public Map<String, Integer> getApplicantsByLocation() {
        // Getting all locations
        Map<String, Integer> locationData = new HashMap<>();
        for (Location location : Location.values()) {
            // Replace underscores with spaces for the location names
            locationData.put(location.name().replaceAll("_"," "), 0);
        }

        // Counting applicants for each location
        String sql = "SELECT location, COUNT(*) AS count FROM applicants GROUP BY location";
        List<Map<String, Object>> results = jdbcTemplate.queryForList(sql);

        // Updating the map with the counts
        for (Map<String, Object> row : results) {
            String location = ((String) row.get("location")).replaceAll("_"," ");
            Integer count = ((Long) row.get("count")).intValue(); // Convert count to Integer
            locationData.put(location, count); // Update the map
        }

        return locationData;
    }

    // The following websites helped me understand how to manipulate the date formatting.
    // https://docs.oracle.com/javase/8/docs/api/java/time/format/DateTimeFormatter.html
    // https://www.w3schools.com/sql/func_mysql_date_format.asp
    public Map<String, Integer> getApplicantsByMonth() {
        Map<String, Integer> monthlyData = new LinkedHashMap<>();
        LocalDate currentDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM yyyy");

        // Initialing the map with the last 12 months
        for (int i = 11; i >= 0; i--) {
            LocalDate monthDate = currentDate.minusMonths(i);
            String month = monthDate.format(formatter);
            monthlyData.put(month, 0);
        }

        // Counting applicants for each month
        String sql = "SELECT DATE_FORMAT(start_date, '%b %Y') AS month, COUNT(*) AS count " +
                "FROM applicants " +
                "WHERE start_date >= DATE_SUB(CURDATE(), INTERVAL 12 MONTH) " +
                "GROUP BY month " +
                "ORDER BY MIN(start_date)";

        List<Map<String, Object>> results = jdbcTemplate.queryForList(sql);

        // Updating the map with the counts
        for (Map<String, Object> row : results) {
            // Needed to replace "Sep" with "Sept" as there was deduplicate data for september due to the abbreviation
            String month = ((String) row.get("month")).replace("Sep", "Sept");
            Integer count = ((Long) row.get("count")).intValue();
            monthlyData.put(month, count);
        }

        return monthlyData;
    }

    public Map<String, Integer> getApplicantsByType() {
        // Counting applicants by type
        String sql = "SELECT is_internal, COUNT(*) AS count FROM applicants GROUP BY is_internal";
        List<Map<String, Object>> results = jdbcTemplate.queryForList(sql);

        Map<String, Integer> typeData = new HashMap<>();
        // Updating the map with the counts
        for (Map<String, Object> row : results) {
            Boolean isInternal = (Boolean) row.get("is_internal");
            String type = isInternal ? "Internal" : "External";
            Integer count = ((Long) row.get("count")).intValue();
            typeData.put(type, count);
        }
        return typeData;
    }

}
