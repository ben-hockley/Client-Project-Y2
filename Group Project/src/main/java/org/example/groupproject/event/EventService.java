package org.example.groupproject.event;

import org.springframework.stereotype.Service;
import java.util.Arrays;
import java.util.List;

@Service
public class EventService {

    // Method to get a list of Event objects
    public List<Event> getAllEvents() {
        // Sample data for demonstration purposes
        return Arrays.asList(
                new Event(1L, "Event 1"),
                new Event(2L, "Event 2"),
                new Event(3L, "Event 3")
        );
    }
}
