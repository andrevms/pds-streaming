package br.com.pds.streaming.framework.media.controllers;

import br.com.pds.streaming.framework.cloud.services.CloudStorageService;
import br.com.pds.streaming.framework.exceptions.InvalidSourceException;
import br.com.pds.streaming.framework.exceptions.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping(value = {"/api/archives", "/api/files"})
public class ArchiveController {

    @Qualifier("amazonS3Service")
    @Autowired
    private CloudStorageService cloudStorageService;

    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> uploadFile(@RequestParam("file") MultipartFile file) {
        try {
            String fileUrl = cloudStorageService.uploadFile(file);
            return ResponseEntity.ok("File uploaded successfully. File URL: " + fileUrl);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to upload file.");
        }
    }

    @DeleteMapping(value = "/delete")
    public ResponseEntity<?> deleteFile(@RequestParam("file") String file) {
        try {
            cloudStorageService.deleteFile(file);
            return ResponseEntity.noContent().build();
        } catch (EntityNotFoundException | InvalidSourceException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(e.getMessage());
        }
    }
}
