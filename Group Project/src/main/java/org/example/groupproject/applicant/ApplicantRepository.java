package org.example.groupproject.applicant;

import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public class ApplicantRepository {

    Applicant applicant1 = new Applicant(1L, "John Doe", "555-555-5555", Location.London,
            "johndoe@gmail.com", ApplicantType.Internal, LocalDate.now());
    Applicant applicant2 = new Applicant(2L, "Jane Doe", "123-456-7890", Location.Wales,
            "janedoe@gmail.com", ApplicantType.External, LocalDate.now());

    List<Applicant> applicantList = List.of(applicant1, applicant2);

    public ApplicantRepository() {
        //constructor
        //will connect to the database when configured
        //DUMMY DATA to test the application
    }
    public List<Applicant> findAll() {
        //will query the database and return all applicants.

        return applicantList;
    }

    public Applicant findById(Long id) {
        return applicantList.get(id.intValue() - 1);
    }
}
