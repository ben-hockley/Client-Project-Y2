package org.example.groupproject.applicant;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

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

    @NotEmpty(message = "Location is required")
    @Pattern(regexp = "^[a-zA-Z0-9 ]+$", message = "Invalid location format")
    @Size(max = maxLocationLength, message = "Location must not exceed " + maxLocationLength + " characters")
    private String location;

    private String event;
    private Boolean isInternal;


    @NotEmpty(message = "Enter the most recent job role of the applicant")
    @Size(max = maxJobRoleLength, message = "Most recent job role must not exceed " + maxJobRoleLength + " characters")
    private String mostRecentJob;

    @NotEmpty(message = "Enter the vacancy this candidate applied for")
    @Size(max = maxVacancyLength, message = "Vacancy applied for must not exceed " + maxVacancyLength + " characters")
    private String vacancyAppliedFor;

    private MultipartFile cv;
}
