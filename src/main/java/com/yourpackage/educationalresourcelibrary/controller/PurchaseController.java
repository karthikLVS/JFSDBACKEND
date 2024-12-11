package com.yourpackage.educationalresourcelibrary.controller;

import com.yourpackage.educationalresourcelibrary.dto.ResourceDto;
import com.yourpackage.educationalresourcelibrary.model.Purchase;
import com.yourpackage.educationalresourcelibrary.model.PurchaseRequest;
import com.yourpackage.educationalresourcelibrary.model.Resource;
import com.yourpackage.educationalresourcelibrary.repository.PurchaseRepository;
import com.yourpackage.educationalresourcelibrary.repository.ResourceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:3000")
public class PurchaseController {

    @Autowired
    private ResourceRepository resourceRepository;

    @Autowired
    private PurchaseRepository purchaseRepository;

    private static final Logger logger = LoggerFactory.getLogger(PurchaseController.class);

    @PostMapping("/purchase")
    public ResponseEntity<String> purchaseBook(@RequestBody PurchaseRequest purchaseRequest) {
        Long bookId = purchaseRequest.getBookId();
        int quantity = purchaseRequest.getQuantity();
        String username = purchaseRequest.getUsername(); // Assuming username is included in PurchaseRequest

        logger.info("Processing purchase for bookId: {} with quantity: {}", bookId, quantity);

        try {
            Resource resource = resourceRepository.findById(bookId).orElse(null);
            if (resource == null) {
                logger.error("Book with ID {} not found", bookId);
                return ResponseEntity.status(400).body("Book not available.");
            }

            if (resource.getQuantity() < quantity) {
                logger.error("Insufficient quantity for book with ID {}: Requested {}, Available {}", bookId, quantity, resource.getQuantity());
                return ResponseEntity.status(400).body("Insufficient quantity.");
            }

            // Update book quantity
            resource.setQuantity(resource.getQuantity() - quantity);
            resourceRepository.save(resource);

            // Save purchase details
            Purchase purchase = new Purchase();
            purchase.setUsername(username);
            purchase.setBookName(resource.getTitle());
            purchase.setQuantity(quantity);
            purchaseRepository.save(purchase);

            logger.info("Purchase successful for bookId: {}", bookId);
            return ResponseEntity.ok("Purchase successful");
        } catch (Exception e) {
            logger.error("Error during purchase", e);
            return ResponseEntity.status(500).body("An error occurred during the purchase. Please try again.");
        }
    }


    @GetMapping("/purchases/{username}")
    public List<ResourceDto> getUserPurchases(@PathVariable String username) {
        List<Purchase> purchases = purchaseRepository.findByUsername(username);
        List<ResourceDto> resources = new ArrayList<>();

        for (Purchase purchase : purchases) {
            Optional<Resource> resource = resourceRepository.findByTitle(purchase.getBookName());
            resource.ifPresent(res -> resources.add(new ResourceDto(res)));
        }
        return resources;
    }


}