package org.example.groupproject.applicant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventApplicantFormService {
    private final EventApplicantFormRepository repository;

    @Autowired
    public EventApplicantFormService(EventApplicantFormRepository repository) {
        this.repository = repository;
    }

    public List<EventApplicantForm> getAllApplicants() {
        return repository.findAll();
    }
}

