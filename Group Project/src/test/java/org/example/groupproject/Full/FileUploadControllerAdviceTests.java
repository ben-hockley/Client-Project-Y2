package org.example.groupproject.Full;

import org.example.groupproject.applicant.FileUploadControllerAdvice;
import org.junit.jupiter.api.Test;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.ModelAndView;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class FileUploadControllerAdviceTests {

    @Test
    public void testHandleMaxSizeException() {
        // Given a MaxUploadSizeExceededException
        MaxUploadSizeExceededException exception = new MaxUploadSizeExceededException(5 * 1024 * 1024);

        // When the exception is handled
        FileUploadControllerAdvice advice = new FileUploadControllerAdvice();
        ModelAndView modelAndView = advice.handleMaxSizeException(exception);

        // Then the error view is returned with the correct error message
        assertNotNull(modelAndView, "The ModelAndView should not be null");
        assertEquals("error/fileUploadError", modelAndView.getViewName(), "The view name should be error/fileUploadError");
        assertEquals("File size must not exceed 5 MB.", modelAndView.getModel().get("errorMessage"), "The error message should be correct");
    }
}