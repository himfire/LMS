package com.example.demo._test;

import com.example.demo.exceptions.CustomException;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.InputStreamSource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.Multipart;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class LocalStorageService {

    private final String uploadPath = "src/main/resources/static/files/";

    private final String baseURL = "http://localhost:8080/api/v1/files/" ;

    public Map<String ,String> save(MultipartFile file, String fileName){
        //stream ,UUID+profile-202002sdfdsf.png
        try {
            Path path = Paths.get(uploadPath);
            Map<String,String> map = new HashMap<>();
            if (!Files.exists(path)) {
                try {
                    Files.createDirectories(path);
                } catch (IOException e) {
                    throw new RuntimeException("Could not create upload folder!");
                }
            }
            String finalFileName = UUID.randomUUID().toString()+ fileName;
            path = path.resolve(finalFileName);
            Files.copy(file.getInputStream(), path);
            System.out.println(baseURL + finalFileName);
            return Map.of(
                        "url", baseURL + finalFileName,
                     "fileName", finalFileName
                );

        }catch(Exception e){
            throw  CustomException.builder().code("Failed to save file").status(HttpStatus.BAD_REQUEST).build();
        }
    }

    public void delete(){

    }

    public FileSystemResource load(String fileName){
        // check if file exists
       try {
//           return new InputStreamResource(
//                   new FileInputStream(new File(Paths.get(uploadPath+fileName).toUri()))
//           );
           return new FileSystemResource(Paths.get(uploadPath+"/"+fileName));
       }catch (Exception e){//
           throw  CustomException.builder().code(e.getMessage()).status(HttpStatus.BAD_REQUEST).build();

       }
    }

}
