package com.yourpackage.educationalresourcelibrary.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    public void sendFeedbackConfirmation(String to, String feedback) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("educonnectlibrarysystem@gmail.com");
        message.setTo(to);
        message.setSubject("Feedback Confirmation");
        message.setText("Thank you for your feedback: " + feedback);
        mailSender.send(message);
    }
}