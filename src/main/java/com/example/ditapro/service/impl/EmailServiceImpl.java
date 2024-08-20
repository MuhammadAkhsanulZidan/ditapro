package com.example.ditapro.service.impl;

import org.springframework.core.io.ResourceLoader;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;

import com.example.ditapro.service.EmailService;

@Service
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender mailSender;
    private final ResourceLoader resourceLoader;

    public EmailServiceImpl(JavaMailSender mailSender, ResourceLoader resourceLoader) {
        this.mailSender = mailSender;
        this.resourceLoader = resourceLoader;
    }

    @Override
    public void sendEmail(String toEmail, String subject, String message) {
        MimeMessagePreparator messagePreparator = mimeMessage -> {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
            messageHelper.setFrom("info.bukuify@gmail.com");
            messageHelper.setTo(toEmail);
            messageHelper.setSubject(subject);

            String content = "<div style=\"padding: 20px; border: 1px solid #ddd; background-color: #f0f4f8;\">" +
            "<div style=\"text-align:center;\">" +
            "<h1 style=\"color: #4a00e0;\">PROCTTOR</h1>" + // Dark purple
            "<h2 style=\"color: #1e90ff;\">Manage Your Project</h2>" + // Dodger blue
            "</div>" +
            message +
            "</div>";                    
            messageHelper.setText(content, true);
        };

        mailSender.send(messagePreparator);
    }
}
