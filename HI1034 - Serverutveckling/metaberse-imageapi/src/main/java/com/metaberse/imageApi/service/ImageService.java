package com.metaberse.imageApi.service;

import com.metaberse.imageApi.records.UploadPicture;
import com.metaberse.imageApi.repository.Image;
import com.metaberse.imageApi.repository.ImageRepository;
import com.metaberse.imageApi.requestHandler.RequestHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class ImageService {
    ImageRepository imageRepository;
    private static final Logger log = LoggerFactory.getLogger(ImageService.class);

    public ImageService(ImageRepository imageRepository) {
        this.imageRepository = imageRepository;
    }

    public String uploadImage(UploadPicture picture) throws BackendException {
        MultipartFile file = picture.file();
        long imageID = 0;
        try {
            if (file == null || file.isEmpty())
                throw new BackendException("No file uploaded");
            var image = new Image();
            image.setType(file.getContentType());
            image.setContent(file.getBytes());
            imageID = imageRepository.save(image).getId();
            var result = updateAPI(picture,imageID);
            log.info("Sent update for : " + result);
            return result;
        } catch (IOException e) {
            if (imageID == 0)
                imageRepository.deleteById(imageID);
            log.error(e.getMessage());
            throw new BackendException("Not able to save image");
        }
    }

    private String updateAPI(UploadPicture picture, long imageID) throws BackendException, IOException {
        switch (picture.type()) {
            case "PROFILE" -> {
                return RequestHandler.updateUser(picture.userID(), imageID);
            }
            case "MESSAGE" -> {
                return RequestHandler.postImageMessage(picture, imageID);
            }
            default -> throw new IllegalStateException("Unexpected value: " + picture.type());
        }
    }


    public ByteArrayResource getAsResource(long id) throws BackendException {
        var imageOP = imageRepository.findById(id);
        if (imageOP.isEmpty())
            throw new BackendException("No such image with id: " + id);
        var image = imageOP.get();
        return new ByteArrayResource(image.getContent());
    }
}
