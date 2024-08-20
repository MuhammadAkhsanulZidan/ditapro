package com.example.ditapro.service.impl;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import com.example.ditapro.service.ImageService;

@Service
public class ImageServiceImpl implements ImageService {
    @Value("${file.upload-dir}")
    private String fileUploadDir;

    @Override
    public String uploadImageFile(String hubfolder, UUID hubUuid, MultipartFile file) {
        try {
            // Create the directory if it doesn't exist
            Path hubDir = Paths.get(fileUploadDir, hubfolder, hubUuid.toString());
            if (!Files.exists(hubDir)) {
                Files.createDirectories(hubDir);
            }

            // Rename the file to the UUID of the workspace
            String filename = hubUuid.toString() + getFileExtension(file.getOriginalFilename());
            Path targetLocation = hubDir.resolve(filename);

            // Write the file to the target location
            try (FileOutputStream fos = new FileOutputStream(targetLocation.toFile())) {
                fos.write(file.getBytes());
            }

            return filename;

        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Could not store file. Please try again!", e);
        }
    }

    @Override
    public Resource getImageFile(String hubFolder, UUID hubUuid, String filename) {
        Path filePath = Paths.get(fileUploadDir, hubFolder, hubUuid.toString(), filename);
        Resource fileResource = new FileSystemResource(filePath.toFile());
        if (fileResource.exists()) {
            return fileResource;
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "File not found");
        }
    }

    // Helper method to get file extension
    private String getFileExtension(String filename) {
        int dotIndex = filename.lastIndexOf('.');
        return dotIndex == -1 ? "" : filename.substring(dotIndex);
    }

    @Override
    public void deleteImageFile(String hubFolder, UUID workspaceUuid, String filename) {
        try {
            Path filePath = Paths.get(fileUploadDir, hubFolder, workspaceUuid.toString(), filename);
            Files.deleteIfExists(filePath);
        } catch (IOException e) {
            throw new RuntimeException("Could not delete file. Please try again!", e);
        }
    }

    @Override
    public String createImageFromName(String color, String name) {
        String[] nameParts = name.split(" ");
        String firstNameInitial = nameParts[0].substring(0, 1).toUpperCase();
        String lastNameInitial = "";

        if (nameParts.length > 1) {
            lastNameInitial = nameParts[nameParts.length - 1].substring(0, 1).toUpperCase();
        }

        String image = color + "/" + firstNameInitial + lastNameInitial;

        return image;
    }

}
