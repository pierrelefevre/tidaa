package com.metaberse.imageApi.records;

import org.springframework.web.multipart.MultipartFile;

import java.sql.Timestamp;

public record UploadPicture(MultipartFile file, long userID, String type, Long chatID, Timestamp timestamp) {

   
}
