package com.yourpackage.educationalresourcelibrary.service;

import com.yourpackage.educationalresourcelibrary.model.Feedback;
import com.yourpackage.educationalresourcelibrary.repository.FeedbackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class FeedbackService {

    @Autowired
    private FeedbackRepository feedbackRepository;

    public Feedback submitFeedback(String feedbackText) {
        Feedback feedback = new Feedback();
        feedback.setFeedback(feedbackText);
        feedback.setSubmittedAt(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        return feedbackRepository.save(feedback);
    }

    public List<Feedback> getAllFeedback() {
        return feedbackRepository.findAll();
    }
}