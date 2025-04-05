package org.example.cloudstorage.controller;

import org.example.cloudstorage.service.StorageService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/files")
public class UploadController {

    private final StorageService storageService;

    public UploadController(StorageService storageService) {
        this.storageService = storageService;
    }

    @PostMapping("/upload")
    public ResponseEntity<String> uploadChunk(
            @RequestParam String fieldId,
            @RequestParam String fileName,
            @RequestParam int chunkIndex,
            @RequestParam MultipartFile file
    ) {
        try {
            storageService.storeChunk(fieldId, fileName, chunkIndex, file);
            return ResponseEntity.ok("Chunk uploaded successfully");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Failed to upload chunk: " + e.getMessage());
        }
    }
}
