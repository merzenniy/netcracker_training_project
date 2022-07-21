package com.example.chargecracker.service.impl;

import com.example.chargecracker.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailService {
    @Autowired
    private JavaMailSender emailSender;

    @Value("${spring.mail.username}")
    private String username;

    @Override
    public void sendMail(String emailTo, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(username);
        if (emailTo == null) {
            message.setTo(username);
        } else {
            message.setTo(emailTo);
        }
        message.setText(text);
        message.setSubject(subject);

        emailSender.send(message);
    }
}
