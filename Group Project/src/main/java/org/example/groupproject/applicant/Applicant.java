package org.example.groupproject.applicant;


import java.time.LocalDate;

public class Applicant {
    private Long id;
    private String name;
    private String phoneNumber;
    private Location location;
    private String email;
    private ApplicantType applicantType;
    private LocalDate startDate;

    public Applicant(Long id,
                     String name,
                     String phoneNumber,
                     Location location,
                     String email,
                     ApplicantType applicantType,
                     LocalDate startDate) {
        this.id = id;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.location = location;
        this.email = email;
        this.applicantType = applicantType;
        this.startDate = startDate;
    }

    public Location getLocation() {
        return location;
    }

    public String getLocationAsString() {
        String locationAsString = location.toString();
        return locationAsString.replaceAll("_", " ");
    }

    public void setLocation(Location location) {
        this.location = location;
    }
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public ApplicantType getApplicantType() {
        return applicantType;
    }

    public String getApplicantTypeAsString() {
        return applicantType.toString();
    }

    public void setApplicantType(ApplicantType applicantType) {
        this.applicantType = applicantType;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public String getStartDateAsString() {
        return startDate.toString();
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }
}
