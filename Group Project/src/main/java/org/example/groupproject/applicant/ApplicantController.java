package org.example.groupproject.applicant;

import org.example.groupproject.applicant.user.User;
import org.example.groupproject.applicant.user.UserRepository;
import org.example.groupproject.filter.Filter;
import org.springframework.security.core.Authentication;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import jakarta.validation.Valid;

@RequestMapping("/applicants")
@RestController
public class ApplicantController {

    private final ApplicantRepository applicantRepository;
    private final EventService eventService;
    private final UserRepository userRepository;


    public ApplicantController(ApplicantRepository applicantRepository, EventService eventService, UserRepository userRepository) {
        this.applicantRepository = applicantRepository;
        this.eventService = eventService;
        this.userRepository = userRepository;
    }
    @GetMapping("/all")
    public ModelAndView allApplicants(@Valid @ModelAttribute("filter") Filter filter, BindingResult bindingResult,
                                      Authentication authentication) {

        ModelAndView modelAndView = new ModelAndView("applicantList");

        Integer eventId = filter.getEventId();
        Boolean isInternal = filter.getIsInternal();
        Location location = filter.getLocation();
        String searchQuery = filter.getSearchQuery();

        String username = authentication.getName();
        User sessionUser = userRepository.findByUsername(username);

        if (bindingResult.hasErrors()) {
            modelAndView.addObject("sessionUser", sessionUser);
            modelAndView.addObject("filter", filter);
            modelAndView.addObject("searchQuery", searchQuery);
            modelAndView.addObject("events", eventService.getAllEvents());
            modelAndView.addObject("locations", List.of(Location.values()));
            modelAndView.addObject("applicants", List.of());
            return modelAndView;
        }

        // If isInternal is false, reset it to null, so all applicants are visible
        if (isInternal != null && !isInternal) {
            isInternal = null;
        }

        List<Applicant> applicants;
        // If no filters are applied, show all applicants
        if ((searchQuery == null || searchQuery.trim().isEmpty()) && eventId == null && isInternal == null &&
                (location == null || location.name().equalsIgnoreCase("All"))) {
            applicants = applicantRepository.findAll();
        // If filters are applied, show applicants based on filters
        } else {
            applicants = applicantRepository.findWithFilters(searchQuery, eventId, isInternal, location);
        }

        List<Event> events = eventService.getAllEvents();
        List<Location> locations = List.of(Location.values());
        modelAndView.addObject("filter", filter);
        modelAndView.addObject("searchQuery", searchQuery);


        modelAndView.addObject("sessionUser", sessionUser);
        modelAndView.addObject("applicants", applicants);
        modelAndView.addObject("events", events);
        modelAndView.addObject("locations", locations);
        return modelAndView;
    }

    @GetMapping("/profile/{id}")
    public ModelAndView viewProfile(@PathVariable Integer id, Authentication authentication){
        Applicant applicant = applicantRepository.findById(id);

        String username = authentication.getName();
        User sessionUser = userRepository.findByUsername(username);

        List<ContactHistory> contactHistory = applicantRepository.getContactHistory(id);

        ModelAndView modelAndView = new ModelAndView("applicantProfile"); // templates/gameDetails.html
        modelAndView.addObject("applicant", applicant);
        modelAndView.addObject("sessionUser", sessionUser);
        modelAndView.addObject("contactHistory", contactHistory);
        return modelAndView;
    }

    @PostMapping("/logContact")
    public void logContact(@RequestBody Map<String, String> contactDetails, Authentication authentication) {
        String subject = contactDetails.get("subject");

        String username = authentication.getName();
        User sessionUser = userRepository.findByUsername(username);

        LocalDateTime now = LocalDateTime.now();

        Integer applicantId = Integer.parseInt(contactDetails.get("applicantId"));
        applicantRepository.logContact(applicantId, sessionUser.getUsername(), subject, now);
    }

}