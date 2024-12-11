package com.yourpackage.educationalresourcelibrary.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.Base64;

@Entity
public class Resource {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String description;
    private String category;
    private String tags;
    private String author;
    private LocalDate publicationDate;
    private int quantity;

    @Lob
    private byte[] coverPhoto;

    @Lob
    private byte[] pdfFile;

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public String getTags() { return tags; }
    public void setTags(String tags) { this.tags = tags; }

    public String getAuthor() { return author; }
    public void setAuthor(String author) { this.author = author; }

    public LocalDate getPublicationDate() { return publicationDate; }
    public void setPublicationDate(LocalDate publicationDate) { this.publicationDate = publicationDate; }

    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }

    public byte[] getCoverPhoto() { return coverPhoto; }
    public void setCoverPhoto(byte[] coverPhoto) { this.coverPhoto = coverPhoto; }

    public byte[] getPdfFile() { return pdfFile; }
    public void setPdfFile(byte[] pdfFile) { this.pdfFile = pdfFile; }

    public String getCoverPhotoBase64() {
        return coverPhoto != null ? Base64.getEncoder().encodeToString(coverPhoto) : null;
    }
}