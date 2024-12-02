package org.example.groupproject.applicant;

import org.example.groupproject.applicant.Event;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class EventService {

    private final JdbcTemplate jdbcTemplate;

    public EventService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // Retrieves all events from the database to be displayed in the applicant form
    public List<Event> getAllEvents() {
        String sql = "SELECT eventid, event_name FROM events";
        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            Event event = new Event();
            event.setId(rs.getLong("eventid"));
            event.setName(rs.getString("event_name"));
            return event;
        });
    }
}
