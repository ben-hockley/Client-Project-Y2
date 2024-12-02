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
            if (applicantRepository.hasBeenInDatabaseForMoreThanAYear(applicant.id().intValue())) {
                applicantRepository.deleteById(applicant.id().intValue());
            }
        }
    }
}
