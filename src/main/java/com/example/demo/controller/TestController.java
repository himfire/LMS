package com.example.demo.controller;

import com.example.demo.domain.service.MailService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/test")
public class TestController {

    private final MailService mailService;

    public TestController(MailService mailService){
        this.mailService = mailService;
    }
    @GetMapping("/mail")
    public void getEmail(){
        StringBuilder body = new StringBuilder();
        body.append("<h1> Thanks for joinging us ! </h1>");
        body.append("<h3> Your verifictaion code is 12548 </h3>");
        body.append("<iframe src=\"https://drive.google.com/file/d/1_sjdHW6CSzEQRWyFwewmTHXQ1ngNyZsv/preview\" width=\"640\" height=\"480\" allow=\"autoplay\"></iframe>");
        mailService.sendEmail("hdalang@gmail.com","testing send email",body.toString());
    }
}
