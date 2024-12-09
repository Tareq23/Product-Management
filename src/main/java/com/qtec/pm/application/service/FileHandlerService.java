package com.qtec.pm.application.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.nio.file.StandardOpenOption;
import java.util.Objects;

@Service
public class FileHandlerService {


    private final String fileDir = "uploads/";
    public String fileHandler(MultipartFile multipartFile) {
        String fileDir = "uploads/";
        try {
            // Ensure the directory exists
            Path uploadPath = Path.of(fileDir);
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            // Save the uploaded file
//            Path filePath = uploadPath.resolve(multipartFile.getOriginalFilename());
            Path filePath = uploadPath.resolve(Objects.requireNonNull(multipartFile.getOriginalFilename()));
            Files.copy(multipartFile.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

            // Process only .java or .jsp files
            String fileName = multipartFile.getOriginalFilename();
            if (fileName.endsWith(".java") || fileName.endsWith(".jsp")) {
                return readTextFile(filePath); // Read the file content
            } else {
                return "Unsupported file type: " + fileName + ". Please upload a .java or .jsp file.";
            }
        } catch (IOException e) {
            return "Error: " + e.getMessage();
        }
    }

    private String readTextFile(Path filePath) throws IOException {
        // Read and return the file content
        return Files.readString(filePath);
    }

    public void editFile(String filePath, String targetText, String replacementText) throws IOException {
        // Read file content
        Path path = Path.of(filePath);
        String content = Files.readString(path);

        // Replace the specific text
        String updatedContent = content.replace(targetText, replacementText);

        // Write updated content back to the file
        Files.writeString(path, updatedContent, StandardOpenOption.TRUNCATE_EXISTING);
    }

}
