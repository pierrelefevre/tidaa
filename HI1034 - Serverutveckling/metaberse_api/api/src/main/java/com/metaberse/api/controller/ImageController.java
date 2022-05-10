package com.metaberse.api.controller;

import com.metaberse.api.DTO.actions.CreateProfilepicDTO;
import com.metaberse.api.model.Image;
import com.metaberse.api.repository.ImageRepository;
import com.metaberse.api.service.BackendException;
import com.metaberse.api.service.ImageService;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@CrossOrigin(origins = "*")
public class ImageController {
    final ImageService imageService;

    public ImageController(ImageService imageService) {
        this.imageService = imageService;
    }

    @PostMapping(value = "/images")
    public ResponseEntity<Long> uploadImage(@ModelAttribute CreateProfilepicDTO profilepicDTO) {
        try {
            return new ResponseEntity<>(imageService.uploadImage(profilepicDTO.file(), profilepicDTO.userID()), HttpStatus.CREATED);
        } catch (BackendException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/images/{id}")
    public ResponseEntity<ByteArrayResource> downloadImage(@PathVariable Long id) {
        try {
            return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(imageService.getAsResource(id));
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.I_AM_A_TEAPOT);
        }
    }
}
