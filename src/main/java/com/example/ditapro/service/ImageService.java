package com.example.ditapro.service;

import java.util.UUID;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface ImageService {
    public String uploadImageFile(String hubFolder, UUID workspaceUuid, MultipartFile file);

    public Resource getImageFile(String hubFolder, UUID workspaceUuid, String filename);

    public void deleteImageFile(String hubFolder, UUID workspaceUuid, String filename);

    public String createImageFromName(String color, String name);
}
