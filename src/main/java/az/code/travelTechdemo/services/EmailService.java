package az.code.travelTechdemo.services;

import az.code.travelTechdemo.entities.EmailDetails;

public interface EmailService {
    String sendSimpleMail(EmailDetails details);

//    String sendMailWithAttachment(EmailDetails details);
}
