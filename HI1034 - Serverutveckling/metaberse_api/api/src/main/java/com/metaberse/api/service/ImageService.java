package com.metaberse.api.service;

import com.metaberse.api.repository.ImageRepository;
import com.metaberse.api.repository.LoadDatabase;
import com.metaberse.api.repository.UserRepository;
import javassist.bytecode.ByteArray;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.metaberse.api.model.Image;
import org.yaml.snakeyaml.util.ArrayUtils;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.Arrays;

@Service
public class ImageService {
    private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);
    private final ImageRepository imageRepository;
    private final UserRepository userRepository;
    private final static String url = "http://localhost:8080/images/";

    public ImageService(ImageRepository imageRepository, UserRepository userRepository){
        this.imageRepository = imageRepository;
        this.userRepository = userRepository;
    }

    public long uploadImage(MultipartFile file, long uId) throws BackendException{
        try {
            var image = new Image();
            image.setType(file.getContentType());
            image.setContent(file.getBytes());
            long id = imageRepository.save(image).getId();
            var userOpt = userRepository.findById(uId);
            if(userOpt.isEmpty())
                return id;
            var user = userOpt.get();
            user.setImageURL(url + id);
            userRepository.save(user);
            return id;
        } catch (IOException e) {
            throw new BackendException("Not able to save image");
        }
    }
    public ByteArrayResource getAsResource(long id) throws BackendException{
        var imageOP = imageRepository.findById(id);
        if(imageOP.isEmpty())
            throw new BackendException("No such image with id: " + id);
        var image = imageOP.get();
        return new ByteArrayResource(image.getContent());
    }
}
