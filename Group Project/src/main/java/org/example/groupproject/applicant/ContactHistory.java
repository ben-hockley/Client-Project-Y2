package org.example.groupproject.applicant;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class ContactHistory {
    private String contactedBy;
    private LocalDateTime contactDate;
    private String contactInfo;
}
