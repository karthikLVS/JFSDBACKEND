
package com.yourpackage.educationalresourcelibrary.controller;

import com.yourpackage.educationalresourcelibrary.model.Feedback;
import com.yourpackage.educationalresourcelibrary.model.User;
import com.yourpackage.educationalresourcelibrary.service.FeedbackService;
import com.yourpackage.educationalresourcelibrary.service.EmailService;
import com.yourpackage.educationalresourcelibrary.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:3000")
public class FeedbackController {

    @Autowired
    private FeedbackService feedbackService;

    @Autowired
    private EmailService emailService;

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/feedback")
    public ResponseEntity<Map<String, String>> submitFeedback(@RequestBody Map<String, String> payload) {
        String feedbackText = payload.get("feedback");
        String username = payload.get("username");

        System.out.println("Received feedback from username: " + username); // Debugging

        User user = userRepository.findByUsername(username);

        if (user != null) {
            System.out.println("User found: " + user.getEmail()); // Debugging
            feedbackService.submitFeedback(feedbackText);
            emailService.sendFeedbackConfirmation(user.getEmail(), feedbackText);
            return ResponseEntity.ok(Map.of("message", "Feedback submitted successfully!"));
        } else {
            System.out.println("User not found: " + username); // Debugging
            return ResponseEntity.badRequest().body(Map.of("message", "User not found. Feedback not sent."));
        }
    }

    @GetMapping("/feedback")
    public ResponseEntity<List<Feedback>> getAllFeedback() {
        List<Feedback> feedbackList = feedbackService.getAllFeedback();
        return ResponseEntity.ok(feedbackList);
    }
}
