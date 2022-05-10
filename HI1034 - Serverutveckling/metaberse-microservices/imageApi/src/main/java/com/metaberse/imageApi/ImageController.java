package com.metaberse.imageApi;

import com.metaberse.imageApi.records.UploadPicture;
import com.metaberse.imageApi.service.BackendException;
import com.metaberse.imageApi.service.ImageService;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*")
public class ImageController {
    @GetMapping("/")
    public String hello() {
        return "Hello it's me MAAAARIO";
    }

    final private ImageService imageService;

    public ImageController(ImageService imageService) {
        this.imageService = imageService;
    }

    @PostMapping(value = "/images")
    public ResponseEntity<Object> uploadProfilePicture(@ModelAttribute UploadPicture profilepicDTO) {
        try {
            return new ResponseEntity<>(imageService.uploadImage(profilepicDTO), HttpStatus.CREATED);
        } catch (BackendException e) {
            return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
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
