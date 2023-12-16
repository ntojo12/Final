package com.auca.sms.controller;

import java.util.Properties;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import jakarta.mail.Authenticator;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

@Controller
public class MemberController {
    
    @PostMapping("/member")
    public ModelAndView processUpdateForm(
            @RequestParam("email") String email,
            @RequestParam("address") String address,
            @RequestParam("number") String number,
            @RequestParam("workingSince") String workingSince,
            @RequestParam("reason") String reason) {
        
        try {
            sendUpdateEmail(email, address, number, workingSince, reason);
            return new ModelAndView("redirect:/index2");
        } catch (Exception e) {
            return new ModelAndView("redirect:/errorpage?error=" + e.getMessage());
        }
    }

    private void sendUpdateEmail(String email, String address, String number, String workingSince, String reason) throws MessagingException {
        // Set up the email properties
        String host = "smtp.gmail.com";
        String port = "587";
        String username = "cmanagement456@gmail.com";
        String password = "uief msqt wgdf ltdp";

        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", port);

        // Create the mail session
        Session mailSession = Session.getInstance(properties, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        // Create the message
        Message message = new MimeMessage(mailSession);
        message.setFrom(new InternetAddress(username));
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email));
        message.setSubject("Thank You For Being Part Of Our Members Club");
        message.setText("You Will Receive Updates On Discounts And Other Important Announcements");

        // Send the message
        Transport.send(message);

        System.out.println("Update submitted successfully. An email confirmation has been sent.");
    }
}
