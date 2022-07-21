package com.example.chargecracker.controller.web;

import com.example.chargecracker.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class EmailController {
    @Autowired
    private EmailService emailService;

    @GetMapping("/report")
    public String sendMessageForm() {
        return "report";
    }

    @PostMapping("/report")
    public String sendMessage(@RequestParam String subject, @RequestParam String text) {
        emailService.sendMail(null, subject, text);
        return "redirect:http://localhost:8090/";
    }
}
