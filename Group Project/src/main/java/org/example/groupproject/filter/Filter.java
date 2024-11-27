package org.example.groupproject.filter;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.groupproject.applicant.Location;


@NoArgsConstructor
@Getter
@Setter
public class Filter {
    private static final int maxSearchLength = 255;

    private Integer eventId;
    private Boolean isInternal;
    private Location location;

    // Adding validation to the search query to prevent SQL injection
    // Currently still allowing the user to enter numbers, in case we add a search by phone number feature
    @Size(max = maxSearchLength, message = "Search query must not exceed " + maxSearchLength + " characters")
    @Pattern(regexp = "^[a-zA-Z0-9\\s]*$", message = "Invalid search query")
    private String searchQuery;
}

