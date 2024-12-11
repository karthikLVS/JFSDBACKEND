package com.yourpackage.educationalresourcelibrary.service;

import com.yourpackage.educationalresourcelibrary.model.Borrow;
import com.yourpackage.educationalresourcelibrary.repository.BorrowRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BorrowService {
    @Autowired
    private BorrowRepository borrowRepository;

    public Borrow saveBorrow(Borrow borrow) {
        return borrowRepository.save(borrow);
    }

    public List<Borrow> findByUsername(String username) {
        return borrowRepository.findByUsername(username);
    }
}
