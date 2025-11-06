package io.c4us.masterbackend.service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import io.c4us.masterbackend.config.EmailService;
import io.c4us.masterbackend.domain.AppUser;
import io.c4us.masterbackend.repo.AppUserRepo;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@Transactional(rollbackOn = Exception.class)
@RequiredArgsConstructor
public class AppUserService {

    @Autowired
    private AppUserRepo appUserRepo;

    @Autowired
    private EmailService emailService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public AppUser createAppUser(AppUser user) {
        user.setUserPassword(passwordEncoder.encode(user.getUserPassword()));
        user.setConfirmationToken(generateAndSetToken(user));
        appUserRepo.save(user);
        if (!user.getUserProfile().equals("MOBILE")) {
            emailService.sendConfirmationEmail(user);
            user.setActive(false);
        }
        AppUser users = appUserRepo.save(user);

        return users;

    }

    public String generateAndSetToken(AppUser user) {
        // 1. Générer un Token robuste (UUID)
        String token = UUID.randomUUID().toString();

        // 2. Définir la durée d'expiration (24 heures)
        LocalDateTime expiryDate = LocalDateTime.now().plusHours(24);

        // 3. Mettre à jour la structure
        user.setConfirmationToken(token);
        user.setTokenExpiryDate(expiryDate);

        // Le service doit ensuite persister ces changements
        // (repository.save(structure))

        return token;
    }

    public Optional<AppUser> findByConfirmationToken(String token) {
        // L'appel utilise l'objet injecté structureRepository
        return appUserRepo.findByConfirmationToken(token);
    }

    public AppUser getAppUser(String id) {
        return appUserRepo.findById(id).orElseThrow(() -> new RuntimeException("User Not found"));
    }

    public AppUser updateAppUser(AppUser us) {
        try {
            AppUser user = getAppUser(us.getId());
            user.setId(us.getId());
            appUserRepo.save(user);
            return user;
        } catch (Exception exception) {
            throw new RuntimeException();
        }
    }

}
