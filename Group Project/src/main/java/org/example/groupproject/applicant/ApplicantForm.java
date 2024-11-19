package org.example.groupproject.applicant;

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

    private String name;
    private String phone;
    private String email;
    private String location;
    private String event;
    private Boolean isInternal;
    private String mostRecentJob;
    private String vacancyAppliedFor;
    private MultipartFile cv;
}
