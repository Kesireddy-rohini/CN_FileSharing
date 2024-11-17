package com.example.service;

import com.example.model.FileRecord;
import com.example.repository.FileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Date;

@Service
public class FileService {

    private final String UPLOAD_DIR = "uploads/";

    @Autowired
    private FileRepository fileRepository;

    public void uploadFile(String fileName, byte[] fileContent) throws Exception {
        Files.write(Paths.get(UPLOAD_DIR + fileName), fileContent);

        FileRecord fileRecord = new FileRecord();
        fileRecord.setFileName(fileName);
        fileRecord.setFilePath(UPLOAD_DIR + fileName);
        fileRecord.setUploadedAt(new Date());
        fileRepository.save(fileRecord);
    }
}