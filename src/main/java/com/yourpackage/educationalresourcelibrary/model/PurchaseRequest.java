package com.yourpackage.educationalresourcelibrary.model;

public class PurchaseRequest {
    private Long bookId;
    private int quantity;
    private String username;

    // Getters and Setters
    public Long getBookId() { return bookId; }
    public void setBookId(Long bookId) { this.bookId = bookId; }

    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
}