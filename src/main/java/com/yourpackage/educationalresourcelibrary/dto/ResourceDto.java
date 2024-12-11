package com.yourpackage.educationalresourcelibrary.dto;

import com.yourpackage.educationalresourcelibrary.model.Resource;

public class ResourceDto {
    private Long id;
    private String title;
    private String description;
    private String category;
    private String tags;
    private String author;
    private String publicationDate;
    private int quantity;
    private String coverPhotoBase64;

    public ResourceDto(Resource resource) {
        this.id = resource.getId();
        this.title = resource.getTitle();
        this.description = resource.getDescription();
        this.category = resource.getCategory();
        this.tags = resource.getTags();
        this.author = resource.getAuthor();
        this.publicationDate = resource.getPublicationDate().toString();
        this.quantity = resource.getQuantity();
        this.coverPhotoBase64 = resource.getCoverPhotoBase64();
    }

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

    public String getPublicationDate() { return publicationDate; }
    public void setPublicationDate(String publicationDate) { this.publicationDate = publicationDate; }

    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }

    public String getCoverPhotoBase64() { return coverPhotoBase64; }
    public void setCoverPhotoBase64(String coverPhotoBase64) { this.coverPhotoBase64 = coverPhotoBase64; }
}