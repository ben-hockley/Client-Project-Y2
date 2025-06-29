package org.example.groupproject.applicant;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
@Entity
@Table(name = "applicants")
public class ApplicantCsv {
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
    @Column(name="currentJobRole")
    private String currentJobRole;
    @Getter
    @Setter
    @Column(name="oldJobRole")
    private String oldJobRole;
    @Getter
    @Setter
    @Column(name="skills")
    private String skills;
    @Getter
    @Setter
    @Column(name="expectedSalary")
    private int expectedSalary;
    @Getter
    @Setter
    @Column(name="qualification")
    private String qualification;
    @Getter
    @Setter
    @Column(name="eventID")
    private int eventID;
    @Getter
    @Setter
    @Column(name="isInternal")
    private boolean isInternal;
    @Getter
    @Setter
    @Column(name="startDate")
    private Date startDate;
    @Getter
    @Setter
    @Column(name="cvFilePath")
    private String cvFilePath;
    @Getter
    @Setter
    @Column(name="isFavourite")
    private boolean isFavourite;

}

