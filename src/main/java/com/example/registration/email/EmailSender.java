package com.example.registration.email;

public interface EmailSender {
    
    void send(String to, String subject, String body);
}
