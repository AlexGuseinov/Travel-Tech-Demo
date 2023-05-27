package az.code.travelTechdemo.services.impl;

import az.code.travelTechdemo.entities.User;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class MailSenderImpl {
    private final JavaMailSender mailSender;
    public void sendPasswordResetEmail(User user, String resetLink) throws IOException {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(user.getEmail());
            message.setSubject("Password Reset");
            message.setText("To reset your password, click on the following link:\n\n" + resetLink);
            mailSender.send(message);
        } catch (Exception e) {
            throw new IOException("uzaq ol");
        }
    }
}
