package io.c4us.masterbackend.ressource;

import java.net.URI;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.c4us.masterbackend.DTOs.LoginRequest;
import io.c4us.masterbackend.config.EmailService;
import io.c4us.masterbackend.domain.AppUser;
import io.c4us.masterbackend.repo.AppUserRepo;
import io.c4us.masterbackend.service.AppUserService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173") // <-- autorise ton frontend
public class AppUserRessource {

    @Autowired
    private final AppUserService appUserService;

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private EmailService emailService;

      @Autowired
    private AppUserRepo appUserRepo;

    @PostMapping
    public ResponseEntity<AppUser> createAppUser(@RequestBody AppUser user) {
        try {
            return ResponseEntity.created(URI.create("/user/userID"))
                    .body(appUserService.createAppUser(user));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/confirm")
    public ResponseEntity<String> confirmStructure(@RequestParam("token") String confirmationToken) {

        // 1. Trouver la structure par le token
        Optional<AppUser> userOpt = appUserService.findByConfirmationToken(confirmationToken);

        if (userOpt.isEmpty()) {
            // headers.add("Location", "http://votre-frontend-domain.com/login");
            // return new ResponseEntity<>("Token de confirmation invalide ou expiré.",
            // HttpStatus.BAD_REQUEST);
            URI redirectUri = URI.create("http://localhost:5173/confirmation-error");
            return ResponseEntity.status(HttpStatus.FOUND).location(redirectUri).build();

        }

        /*
         * if (structureOpt.getTokenExpiryDate().isBefore(LocalDateTime.now())) {
         * // Le token est expiré. Optionnel: on peut nettoyer le token expiré ici
         * return new ResponseEntity<>("Token de confirmation invalide ou expiré.",
         * HttpStatus.BAD_REQUEST);
         * }
         */

        AppUser appUser = userOpt.get();

        // 2. Mettre à jour isActive à "true"
        appUser.setActive(true);
        appUser.setConfirmationToken(null); // Optionnel: Invalider le token après usage
        appUserService.updateAppUser(appUser);

        // 3. Rediriger ou retourner un message de succès
        // headers.add("Location", "http://localhost:5173/login");
        // return new ResponseEntity<>("Votre structure a été activée avec succès!",
        // HttpStatus.OK);

        URI redirectUri = URI.create("http://localhost:5173/login?confirmed=true");
        return ResponseEntity.status(HttpStatus.FOUND).location(redirectUri).build();

        // Vous pouvez aussi utiliser un RedirectView pour rediriger vers une page de
        // succès
    }

    @PostMapping("/resend-confirmation")
    public ResponseEntity<String> resendConfirmation(@RequestBody Map<String, String> request) {
        String email = request.get("email");
        boolean sent = emailService.resendConfirmationEmail(email);

        if (sent) {
            return ResponseEntity.ok("Un nouveau lien de confirmation a été envoyé à " + email);
        } else {
            return ResponseEntity.badRequest().body("Aucun compte trouvé pour cet e-mail ou déjà confirmé.");
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody LoginRequest request) {
        AppUser user = appUserRepo.findByUserEmail(request.getEmail());

        if (user == null || !passwordEncoder.matches(request.getPassword(), user.getUserPassword())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Email ou mot de passe incorrect");
        }

        if (!user.isActive()) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body("Veuillez confirmer votre adresse e-mail avant de vous connecter.");
        }

        return ResponseEntity.ok(Map.of(
                "message", "Connexion réussie",
                "userName", user.getUserName(),
                "userEmail", user.getUserEmail()));
    }

}
