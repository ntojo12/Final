package com.auca.sms.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import java.util.Properties;

@Controller
public class SubmissionController {
	
	@GetMapping("/index2")
    public String index2() {
        return "index2";
    }

	@GetMapping("/membership")
    public String membership() {
        return "membership";
    }

    @PostMapping("/update")
    public ModelAndView processUpdateForm(@RequestParam("email") String email, @RequestParam("update") String update) {
        try {
            sendUpdateEmail(email, update);
            return new ModelAndView("redirect:/index2");
        } catch (Exception e) {
            return new ModelAndView("redirect:/errorpage?error=" + e.getMessage());
        }
    }

    private void sendUpdateEmail(String email, String update) {
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

        try {
            // Create the message
            Message message = new MimeMessage(mailSession);
            message.setFrom(new InternetAddress(username));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email));
            message.setSubject("Thank You For Placing Your order");
            message.setText("Your Order Will Reach To You in 30 minutes. Please Verify You Ordered this: " + update);

            // Send the message 
            Transport.send(message);

            System.out.println("Update submitted successfully. An email confirmation has been sent.");
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}
