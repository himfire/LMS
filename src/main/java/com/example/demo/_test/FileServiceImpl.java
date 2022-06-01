package com.example.demo._test;

import com.example.demo.exceptions.CustomException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@Service
public class FileServiceImpl implements FileService{

    private final AppFileRepo appFileRepo;
    private final LocalStorageService localStorageService;

    @Autowired
    public FileServiceImpl(AppFileRepo appFileRepo, LocalStorageService localStorageService) {
        this.appFileRepo = appFileRepo;
        this.localStorageService = localStorageService;
    }

    @Override
    public AppFile save(MultipartFile file) {
        final Map<String, String> savedFileData = localStorageService.save(file, file.getOriginalFilename());
        final AppFile appFile = AppFile.builder()
                .fileName(savedFileData.get("fileName"))
                .url(savedFileData.get("url"))
                .size(file.getSize())
                .type(file.getContentType())
                .build();
        return appFileRepo.save(appFile);
    }

    @Override
    public void delete() {

    }

    @Override
    public FileSystemResource load(String fileName) {
        if ( ! appFileRepo.existsByFileName(fileName) ){
            throw CustomException.builder().status(HttpStatus.NOT_FOUND).build();
        }
        return localStorageService.load(fileName);
    }
}
