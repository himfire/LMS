package com.example.demo.domain.service.impl;

import com.example.demo.domain.service.MailService;
import com.example.demo.exceptions.CustomException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MailServiceImpl implements MailService {

    @Autowired
    private  JavaMailSender javaMailSender;
    public void sendEmail(String destination, String subject, String msg) {
        try{
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom("hopensharebusiness@gmail.com");
            message.setTo(destination);
            message.setSubject(subject);
            message.setText(msg);
            javaMailSender.send(message);
        }
        catch (Exception e){
            throw CustomException.builder()
                    .code("Failed to send mail, error: " + e.getMessage())
                    .status(HttpStatus.BAD_REQUEST)
                    .build();
        }
    }

    public void sendEmail(List<String> destinations, String subject, String msg) {
        try{

            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom("hopensharebusiness@gmail.com");
            destinations.forEach(destination -> {
                message.setTo(destination);
                message.setSubject(subject);
                message.setText(msg);
                javaMailSender.send(message);
            });

        }
        catch (Exception e){
            throw CustomException.builder()
                    .code("Failed to send mail, error: "+e.getMessage())
                    .status(HttpStatus.BAD_REQUEST)
                    .build();
        }
    }
}
