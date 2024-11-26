package org.example.groupproject.filter;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.groupproject.applicant.Location;


@NoArgsConstructor
@Getter
@Setter
public class Filter {
    private Integer eventId;
    private Boolean isInternal;
    private Location location;
}

