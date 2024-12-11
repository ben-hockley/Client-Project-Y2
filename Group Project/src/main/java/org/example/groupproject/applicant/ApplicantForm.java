package org.example.groupproject.applicant;

import jakarta.validation.constraints.*;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;
// Used Spring documentation to grasp a better understanding of how to upload files
// https://spring.io/guides/gs/uploading-files

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ApplicantForm {
    // Creating constants to avoid magic numbers
    private static final int maxNameLength = 50;
    private static final int minPhoneLength = 10;
    private static final int maxPhoneLength = 15;
    private static final int maxEmailLength = 100;
    private static final int maxLocationLength = 100;
    private static final int maxJobRoleLength = 255;
    private static final int maxVacancyLength = 255;
    private static final int maxSkillsLength = 1000;
    private static final int maxExpectedSalary = 9999999;
    private static final int maxQualificationLength = 255;

    @NotEmpty(message = "Name is required")
    @Size(max = maxNameLength, message = "Name must not exceed " + maxNameLength + " characters")
    private String name;

    // Used this webpage to help format a Java regular expression
    // https://www.jrebel.com/blog/java-regular-expressions-cheat-sheet
    @Pattern(regexp = "^\\+?[0-9\\- ]+$", message = "Invalid phone number format")
    @Size(min = minPhoneLength, max = maxPhoneLength,
            message = "Phone number must be between " + minPhoneLength + " and " + maxPhoneLength + " characters")
    private String phone;


    @Email(message = "Invalid email address")
    @NotEmpty(message = "Email is required")
    @Size(max = maxEmailLength, message = "Email must not exceed " + maxEmailLength + " characters")
    private String email;

    @NotNull(message = "Location is required")
    private Location location;

    private String event;
    private Boolean isInternal;


    @NotEmpty(message = "Enter the most recent job role of the applicant")
    @Size(max = maxJobRoleLength, message = "Most recent job role must not exceed " + maxJobRoleLength + " characters")
    private String mostRecentJob;

    @NotEmpty(message = "Enter the vacancy this candidate applied for")
    @Size(max = maxVacancyLength, message = "Vacancy applied for must not exceed " + maxVacancyLength + " characters")
    private String vacancyAppliedFor;

    @NotEmpty(message = "Enter the relevant skills of the applicant")
    @Size(max = maxSkillsLength, message = "Skills must not exceed " + maxSkillsLength + " characters")
    @Pattern(regexp = "^[a-zA-Z, ]+$", message = "Invalid skills format")
    private String relevantSkills;

    @NotEmpty(message = "Expected salary is required")
    @Size(max = maxExpectedSalary, message = "Expected salary must not exceed " + maxExpectedSalary + " characters")
    @Pattern(regexp = "^[0-9]+$", message = "Salary must only include numbers")
    private String expectedSalary;

    @NotEmpty(message = "Enter the qualification of the applicant")
    @Size(max = maxQualificationLength, message = "Qualification must not exceed " + maxQualificationLength + " characters")
    @Pattern(regexp = "^[a-zA-Z ]+$", message = "Invalid qualification format")
    private String qualification;

    private MultipartFile cv;
}
