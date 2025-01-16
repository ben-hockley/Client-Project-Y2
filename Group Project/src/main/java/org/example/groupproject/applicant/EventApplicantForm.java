package org.example.groupproject.applicant;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;

@Entity
public class EventApplicantForm {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "Name is required")
    private String name;

    @NotEmpty(message = "Email is required")
    @Email(message = "Invalid email address")
    private String email;

    @NotEmpty(message = "Interest is required")
    private String interest;

    @NotEmpty(message = "Who you spoke to is required")
    private String spokeTo;

    @NotEmpty(message = "Event attended is required")
    private String eventAttended;

    private String extraInfo;

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getInterest() {
        return interest;
    }

    public void setInterest(String interest) {
        this.interest = interest;
    }

    public String getSpokeTo() {
        return spokeTo;
    }

    public void setSpokeTo(String spokeTo) {
        this.spokeTo = spokeTo;
    }

    public String getEventAttended() {
        return eventAttended;
    }

    public void setEventAttended(String eventAttended) {
        this.eventAttended = eventAttended;
    }

    public String getExtraInfo() {
        return extraInfo;
    }

    public void setExtraInfo(String extraInfo) {
        this.extraInfo = extraInfo;
    }
}
