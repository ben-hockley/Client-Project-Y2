package org.example.groupproject.event;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class EventService {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
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
