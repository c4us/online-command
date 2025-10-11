package io.c4us.masterbackend.config;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import io.c4us.masterbackend.domain.AppUser;
import io.c4us.masterbackend.repo.AppUserRepo;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private AppUserRepo appUserRepo;

    public void sendConfirmationEmail(AppUser user) {
        SimpleMailMessage message = new SimpleMailMessage();

        // 1. Définir l'URL de confirmation
        // L'URL devrait pointer vers votre contrôleur de confirmation, e.g.,
        // "http://localhost:8080/api/structures/confirm?token=" +
        // structure.getConfirmationToken()
        String confirmationUrl = "http://localhost:8080/" + "user/confirm?token=" + user.getConfirmationToken();

        // 2. Préparer le message
        message.setTo(user.getUserEmail());
        message.setSubject("Confirmation de la création de votre structure : " + user.getUserName());
        message.setText("Bonjour " + user.getUserName() + ",\n\n"
                + "Veuillez cliquer sur le lien ci-dessous pour activer votre structure et finaliser l'inscription :\n"
                + confirmationUrl + "\n\n"
                + "Merci.");

        // 3. Envoyer l'e-mail
        mailSender.send(message);
    }

    public boolean resendConfirmationEmail(String email) {
        AppUser user = appUserRepo.findByUserEmail(email);
        if (user == null || user.isActive()) {
            return false; // compte inexistant ou déjà confirmé
        }
        user.setConfirmationToken(UUID.randomUUID().toString());
        appUserRepo.save(user);
        sendConfirmationEmail(user);
        return true;
    }

}