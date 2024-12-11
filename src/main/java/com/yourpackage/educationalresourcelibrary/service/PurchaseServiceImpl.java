package com.yourpackage.educationalresourcelibrary.service;

import com.yourpackage.educationalresourcelibrary.model.Purchase;
import com.yourpackage.educationalresourcelibrary.model.PurchaseRequest;
import com.yourpackage.educationalresourcelibrary.model.Resource;
import com.yourpackage.educationalresourcelibrary.repository.PurchaseRepository;
import com.yourpackage.educationalresourcelibrary.repository.ResourceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class PurchaseServiceImpl implements PurchaseService {

    @Autowired
    private ResourceRepository resourceRepository;

    @Autowired
    private PurchaseRepository purchaseRepository;

    private static final Logger logger = LoggerFactory.getLogger(PurchaseServiceImpl.class);

    @Override
    public ResponseEntity<String> purchaseBook(PurchaseRequest purchaseRequest) {
        logger.info("Received purchase request: {}", purchaseRequest);

        Long bookId = purchaseRequest.getBookId();
        int quantity = purchaseRequest.getQuantity();
        String username = purchaseRequest.getUsername();

        try {
            Resource resource = resourceRepository.findById(bookId).orElse(null);
            if (resource == null) {
                logger.error("Book with ID {} not found", bookId);
                return ResponseEntity.status(400).body("Book not available.");
            }

            logger.info("Available quantity for book ID {}: {}", bookId, resource.getQuantity());

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
}