package com.example.trellobackend.services.impl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
    @Autowired
    JavaMailSender javaMailSender;

    public void sendEmail(String toEmail, String subject, String content) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom("nbtrung0311@gmail.com");
        mailMessage.setTo(toEmail);
        mailMessage.setSubject(subject);
        mailMessage.setText(content);
        javaMailSender.send(mailMessage);
    }

    public void sendInvitationEmail(String email, String inviteLink) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("nbtrung0311@gmail.com");
        message.setTo(email);
        message.setSubject("Invitation to my workspace");
        message.setText("You have been invited my workspace, click this link to join: " + inviteLink);
        javaMailSender.send(message);
    }
}
