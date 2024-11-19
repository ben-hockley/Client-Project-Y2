package org.example.groupproject.applicant;

import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
public class ApplicantFormService {

    private final List<ApplicantForm> applicantForms = new ArrayList<>();

    public void saveApplicantForm(ApplicantForm applicantForm) {
        applicantForms.add(applicantForm); // Until we have a database, we will store the data in memory
        System.out.println("Applicant Form: " + applicantForm);
    }

    public List<ApplicantForm> getAllApplicantForms() {
        return new ArrayList<>(applicantForms);
    }
}
