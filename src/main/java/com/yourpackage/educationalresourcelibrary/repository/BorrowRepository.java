package com.yourpackage.educationalresourcelibrary.repository;

import com.yourpackage.educationalresourcelibrary.model.Borrow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BorrowRepository extends JpaRepository<Borrow, Long> {
    List<Borrow> findByUsername(String username);
}
