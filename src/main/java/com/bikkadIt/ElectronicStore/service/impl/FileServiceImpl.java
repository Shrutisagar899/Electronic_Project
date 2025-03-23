package com.bikkadIt.ElectronicStore.service.impl;

import com.bikkadIt.ElectronicStore.service.FileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class FileServiceImpl implements FileService {

    private Logger logger = LoggerFactory.getLogger(FileServiceImpl.class);

    @Override
    public String uploadFile(MultipartFile file, String path) throws IOException {
        // Validate file input
        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException("File is empty or not provided.");
        }

        // Validate and sanitize the original file name
        String originalFileName = file.getOriginalFilename();
        if (originalFileName == null) {
            throw new IllegalArgumentException("Original file name is null.");
        }
        String safeFileName = originalFileName.replaceAll("[^a-zA-Z0-9\\.\\-]", "_");

        // Generate a unique file name
        String filename = UUID.randomUUID().toString();
        String fileName = filename.concat(safeFileName.substring(safeFileName.lastIndexOf(".")));

        // Create directories if they don't exist
        File dir = new File(path);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        // Save the file to the specified path
        String filepath = path + File.separator + fileName;
        try {
            Files.copy(file.getInputStream(), Paths.get(filepath));
        } catch (IOException e) {
            logger.error("Failed to save the file: {}", e.getMessage());
            throw new IOException("Could not save file: " + fileName, e);
        }

        logger.info("File uploaded successfully: {}", fileName);
        return fileName;
    }

    //   Files.copy(file.getInputStream(), Paths.get(filepath));
    // return fileName;
//        //String extention = "";
//        int lastIndexOfDot = originalFileName.lastIndexOf(".");
//        if (lastIndexOfDot != -1) {
//            extention = originalFileName.substring(lastIndexOfDot);
//        } else {
//            throw new BadApiRequest("Invalid file format! File must have an extension.");
//        }
//
//        String fileNameWithExtension = filename + extention;
//        String fullPathWithFilename = path + fileNameWithExtension;
//          logger.info("full image path: {}" , fullPathWithFilename);
//        if (extention.equalsIgnoreCase(".png") ||
//                extention.equalsIgnoreCase(".jpg") ||
//                extention.equalsIgnoreCase(".jpeg")) {
//            logger.info("file extention is {}",extention);
//            File folder = new File(path);
//            if (!folder.exists()) {
//                folder.mkdirs(); // create the folder if it doesn't exist
//            }
//
//            // Upload the file
//            Files.copy(file.getInputStream(), Paths.get(fullPathWithFilename), StandardCopyOption.REPLACE_EXISTING);
//
//            return fileNameWithExtension;
//        } else {
//            throw new BadApiRequest("File with extension " + extention + " not allowed!");
//        }


    @Override
    public InputStream getResource(String path, String name) throws FileNotFoundException {
        String fullpath = path + name;
        return new FileInputStream(fullpath);
    }
}
