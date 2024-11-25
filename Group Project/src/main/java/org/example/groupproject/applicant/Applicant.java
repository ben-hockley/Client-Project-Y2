package org.example.groupproject.applicant;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
@Entity
public class Applicant {
    @Id
    @Getter
    @Setter
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Getter
    @Setter
    private String name;
    @Getter
    @Setter
    private String email;
    @Getter
    @Setter
    private String phone;
    @Getter
    @Setter
    private String location;
    @Getter
    @Setter
    private String currentJobRole;
    @Getter
    @Setter
    private String oldJobRole;
    @Getter
    @Setter
    private int eventID;
    @Getter
    @Setter
    private boolean isInternal;
    @Getter
    @Setter
    private Date startDate;
    @Getter
    @Setter
    private String cvFilePath;

}

