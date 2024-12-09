package org.example.groupproject.applicant;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ApplicantScheduler {
    private final ApplicantRepository applicantRepository;

    public ApplicantScheduler(ApplicantRepository applicantRepository) {
        this.applicantRepository = applicantRepository;
    }

    @Scheduled(cron = "0 0 0 * * * ")
    public void deleteApplicantsInDatabaseForMoreThanAYear() {
        List<Applicant> applicantList = applicantRepository.findAll();
        for (Applicant applicant : applicantList) {
            //applicants that are not favourites are automatically deleted after a year
            if (applicantRepository.hasBeenInDatabaseForMoreThanAYear(applicant.id().intValue()) && !applicant.isFavourite()) {
                applicantRepository.deleteById(applicant.id().intValue());
            }
        }
    }
}
