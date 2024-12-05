package org.example.groupproject.applicant;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class FileUploadControllerAdvice {

    private static final long maxFileSizeMB = 1; // Maximum file size in MB

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ModelAndView handleMaxSizeException(MaxUploadSizeExceededException ex) {
        ModelAndView modelAndView = new ModelAndView("error/fileUploadError");
        modelAndView.addObject("errorMessage", "File size must not exceed "
                + maxFileSizeMB + " MB.");
        return modelAndView;
    }
}
