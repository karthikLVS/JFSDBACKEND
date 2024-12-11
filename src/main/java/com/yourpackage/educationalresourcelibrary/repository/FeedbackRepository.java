package com.yourpackage.educationalresourcelibrary.repository;

import com.yourpackage.educationalresourcelibrary.model.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FeedbackRepository extends JpaRepository<Feedback, Long> {
}