package org.example.groupproject.applicant;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
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

            eventData.put(eventName, applicantCount);
        }

        return eventData;
    }
}
