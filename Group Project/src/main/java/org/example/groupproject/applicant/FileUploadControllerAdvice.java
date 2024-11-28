package org.example.groupproject.applicant;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.ModelAndView;

// The following webpage helped with implementing the exception handler:
// https://medium.com/@bereketberhe27/mastering-global-exception-handling-in-spring-boot-a-practical-guide-fe946b3eaeb9

@ControllerAdvice
public class FileUploadControllerAdvice {
    private static final long maxFileSizeMB = 1;


    private final EventService eventService;

    public FileUploadControllerAdvice(EventService eventService) {
        this.eventService = eventService;
    }

    // Exception handler for when the file size exceeds the maximum
    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ModelAndView handleMaxSizeException(MaxUploadSizeExceededException ex) {
        ModelAndView modelAndView = new ModelAndView("applicant/applicantForm");
        modelAndView.addObject("errorMessage",
                "File size must not exceed " + maxFileSizeMB + " MB.");
        modelAndView.addObject("applicantForm", new ApplicantForm());
        modelAndView.addObject("events", eventService.getAllEvents());
        return modelAndView;
    }
}

