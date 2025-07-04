package org.example.groupproject.applicant;

import lombok.Getter;
import lombok.Setter;

public class EmailRequest {
    private String to;
    private String subject;
    private String message;

    public EmailRequest() {
    }

    public EmailRequest(String to, String subject, String text) {
        this.to = to;
        this.subject = subject;
        this.message = text;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
