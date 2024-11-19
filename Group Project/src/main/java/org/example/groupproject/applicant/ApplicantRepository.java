package org.example.groupproject.applicant;

import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public class ApplicantRepository {

    public ApplicantRepository() {
        //constructor
        //will connect to the database when configured
    }
    public List<Applicant> findAll() {
        //will query the database and return all applicants.

        //PLACEHOLDER CODE FOR ApplicantController
        Applicant applicant1 = new Applicant(1L, "John Doe", "555-555-5555",
                "johndoe@gmail.com", ApplicantType.INTERNAL, LocalDate.now());
        Applicant applicant2 = new Applicant(2L, "Jane Doe", "123-456-7890",
                "janedoe@gmail.com", ApplicantType.EXTERNAL, LocalDate.now());

        return List.of(applicant1, applicant2);
    }
}
