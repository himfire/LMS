package com.example.demo.domain.service;

import java.util.List;

public interface MailService {
    void sendEmail(String destination, String subject,String msg);
    void sendEmail(List<String> destination, String subject,String msg);
}
