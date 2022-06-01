package com.example.demo._test;

import org.springframework.core.io.FileSystemResource;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

public interface FileService {

    public AppFile save(MultipartFile file);
    public void delete();
    public FileSystemResource load(String fileName);

}
