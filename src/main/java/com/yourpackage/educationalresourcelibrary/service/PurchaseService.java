package com.yourpackage.educationalresourcelibrary.service;

import com.yourpackage.educationalresourcelibrary.model.PurchaseRequest;
import org.springframework.http.ResponseEntity;

public interface PurchaseService {
    ResponseEntity<String> purchaseBook(PurchaseRequest purchaseRequest);
}