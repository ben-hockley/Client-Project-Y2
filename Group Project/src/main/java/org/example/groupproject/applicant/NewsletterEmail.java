package org.example.groupproject.applicant;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class NewsletterEmail {

    @NotNull
    @Size(min = 1, message = "Subject is required")
    private String subject;

    private boolean emailToAll;

    // Getters and setters

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public boolean isEmailToAll() {
        return emailToAll;
    }

    public void setEmailToAll(boolean emailToAll) {
        this.emailToAll = emailToAll;
    }
}
