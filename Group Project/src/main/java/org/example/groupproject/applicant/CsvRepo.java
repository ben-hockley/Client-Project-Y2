package org.example.groupproject.applicant;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CsvRepo extends JpaRepository<Applicant, Integer> {
}
