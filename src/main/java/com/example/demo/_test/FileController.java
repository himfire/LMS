package com.example.demo._test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("api/v1/files")
@CrossOrigin("*")
public class FileController {

    @Autowired
    private FileService fileService;

    @PostMapping("/upload")
    public AppFile upload(@RequestParam MultipartFile file) throws IOException {
        System.out.println("upload...");
        return fileService.save(file );
    }


    @GetMapping(value = "/{fileURI}", produces = MediaType.IMAGE_JPEG_VALUE)
    public ResponseEntity<FileSystemResource> download(@PathVariable String fileURI) throws IOException {
        System.out.println("download...");
       return  ResponseEntity.status(HttpStatus.OK).body( fileService.load(fileURI));
    }


}
