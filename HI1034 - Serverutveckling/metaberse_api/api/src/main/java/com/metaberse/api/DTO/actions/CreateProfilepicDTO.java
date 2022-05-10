package com.metaberse.api.DTO.actions;

import org.springframework.web.multipart.MultipartFile;

public record CreateProfilepicDTO(MultipartFile file, long userID) {
}
