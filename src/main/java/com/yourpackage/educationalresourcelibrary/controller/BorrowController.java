package com.yourpackage.educationalresourcelibrary.controller;

import com.yourpackage.educationalresourcelibrary.model.Borrow;
import com.yourpackage.educationalresourcelibrary.model.Resource;
import com.yourpackage.educationalresourcelibrary.service.BorrowService;
import com.yourpackage.educationalresourcelibrary.service.ResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/resources")
@CrossOrigin(origins = "http://localhost:3000")
public class BorrowController {
    @Autowired
    private BorrowService borrowService;

    @Autowired
    private ResourceService resourceService;

    @PostMapping("/{id}/borrow")
    public ResponseEntity<Borrow> borrowResource(@PathVariable Long id, @RequestBody Borrow borrow) {
        Resource resource = resourceService.getResourceById(id);
        if (resource == null) {
            return ResponseEntity.notFound().build();
        }
        borrow.setResource(resource);
        borrow.setResourceTitle(resource.getTitle());
        borrow.setBorrowDate(new Date());
        borrow.setDeadline(new Date(System.currentTimeMillis() + (borrow.getBorrowPeriod() * 86400000L))); // Setting deadline based on the borrow period
        Borrow savedBorrow = borrowService.saveBorrow(borrow);
        return ResponseEntity.ok(savedBorrow);
    }

    @GetMapping("/borrowed/{username}")
    public ResponseEntity<List<Borrow>> getBorrowedBooks(@PathVariable String username) {
        List<Borrow> borrowedBooks = borrowService.findByUsername(username);
        if (borrowedBooks.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(borrowedBooks);
    }

}
