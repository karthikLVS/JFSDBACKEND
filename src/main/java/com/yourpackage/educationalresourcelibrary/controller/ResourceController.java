package com.yourpackage.educationalresourcelibrary.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.yourpackage.educationalresourcelibrary.model.Resource;
import com.yourpackage.educationalresourcelibrary.service.ResourceService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:3000")
public class ResourceController {

    @Autowired
    private ResourceService resourceService;

    @PostMapping("/upload")
    public ResponseEntity<Map<String, String>> uploadResource(
            @RequestParam("title") String title,
            @RequestParam("description") String description,
            @RequestParam("category") String category,
            @RequestParam("tags") String tags,
            @RequestParam("author") String author,
            @RequestParam("publicationDate") String publicationDate,
            @RequestParam(value = "coverPhoto", required = false) MultipartFile coverPhoto,
            @RequestParam(value = "pdfFile", required = false) MultipartFile pdfFile) {

        try {
            Resource resource = new Resource();
            resource.setTitle(title);
            resource.setDescription(description);
            resource.setCategory(category);
            resource.setTags(tags);
            resource.setAuthor(author);
            resource.setPublicationDate(LocalDate.parse(publicationDate));

            if (coverPhoto != null) {
                resource.setCoverPhoto(coverPhoto.getBytes());
            }
            if (pdfFile != null) {
                resource.setPdfFile(pdfFile.getBytes());
            }

            resourceService.saveResource(resource);

            Map<String, String> response = new HashMap<>();
            response.put("message", "Resource uploaded successfully!");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("message", "Error uploading resource"));
        }
    }
    
    @GetMapping("/resources")
    public ResponseEntity<List<Map<String, Object>>> getAllResources() {
        List<Resource> resources = resourceService.getAllResources();
        List<Map<String, Object>> resourceDetails = resources.stream().map(resource -> {
            Map<String, Object> map = new HashMap<>();
            map.put("id", resource.getId());
            map.put("title", resource.getTitle());
            map.put("description", resource.getDescription());
            map.put("category", resource.getCategory());
            map.put("author", resource.getAuthor());
            map.put("publicationDate", resource.getPublicationDate());
            if (resource.getCoverPhoto() != null) {
                String coverPhotoUrl = "/api/resources/" + resource.getId() + "/cover";
                map.put("coverPhotoUrl", coverPhotoUrl);
            }
            return map;
        }).toList();

        return ResponseEntity.ok(resourceDetails);
    }

    // Endpoint to serve cover photo
    @GetMapping("/resources/{id}/cover")
    public ResponseEntity<byte[]> getCoverPhoto(@PathVariable Long id) {
        Resource resource = resourceService.getResourceById(id);
        if (resource.getCoverPhoto() != null) {
            return ResponseEntity.ok()
                    .header("Content-Type", "image/jpeg")
                    .body(resource.getCoverPhoto());
        }
        return ResponseEntity.notFound().build();
    }
    
    @GetMapping("/resources/{id}")
    public ResponseEntity<Map<String, Object>> getResourceById(@PathVariable Long id) {
        Resource resource = resourceService.getResourceById(id);
        Map<String, Object> response = new HashMap<>();
        response.put("id", resource.getId());
        response.put("title", resource.getTitle());
        response.put("description", resource.getDescription());
        response.put("category", resource.getCategory());
        response.put("author", resource.getAuthor());
        response.put("publicationDate", resource.getPublicationDate());
        if (resource.getCoverPhoto() != null) {
            response.put("coverPhotoUrl", "/api/resources/" + resource.getId() + "/cover");
        }
        if (resource.getPdfFile() != null) {
            response.put("pdfFileUrl", "/api/resources/" + resource.getId() + "/pdf");
        }
        return ResponseEntity.ok(response);
    }

    @GetMapping("/resources/{id}/pdf")
    public ResponseEntity<byte[]> getPdfFile(@PathVariable Long id) {
        Resource resource = resourceService.getResourceById(id);
        if (resource.getPdfFile() != null) {
            return ResponseEntity.ok()
                    .header("Content-Type", "application/pdf")
                    .body(resource.getPdfFile());
        }
        return ResponseEntity.notFound().build();
    }
    
   
    
    

    @PutMapping("/{id}")
    public ResponseEntity<Resource> updateResource(@PathVariable Long id, @RequestBody Resource updatedResource) {
        Resource resource = resourceService.getResourceById(id);
        resource.setTitle(updatedResource.getTitle());
        resource.setDescription(updatedResource.getDescription());
        resource.setCategory(updatedResource.getCategory());
        resource.setTags(updatedResource.getTags());
        resource.setAuthor(updatedResource.getAuthor());
        resourceService.saveResource(resource);
        return ResponseEntity.ok(resource);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteResource(@PathVariable Long id) {
        resourceService.deleteResourceById(id);
        return ResponseEntity.ok("Resource deleted successfully");
    }
    
    @GetMapping
    public List<Resource> getResources(@RequestParam(required = false) String category) {
        if (category != null) {
            return resourceService.getResourcesByCategory(category);
        }
        return resourceService.getAllResources();
    }
    
}
    

    

