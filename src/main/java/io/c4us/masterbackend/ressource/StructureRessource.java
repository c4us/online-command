package io.c4us.masterbackend.ressource;

import java.net.URI;
import java.util.Optional;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.c4us.masterbackend.domain.Structure;
import io.c4us.masterbackend.service.StructureService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/structure/")
@RequiredArgsConstructor
public class StructureRessource {

    private final StructureService structureService;

    
    @PostMapping
    public ResponseEntity<Structure> createStructure(@RequestBody Structure struct) {
        try {
            return ResponseEntity.created(URI.create("/structure/userID"))
                    .body(structureService.createStructure(struct));


        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }

    // ... suite du StructureController
    
    @GetMapping("/confirm")
    public ResponseEntity<String> confirmStructure(@RequestParam("token") String confirmationToken) {
            HttpHeaders headers = new HttpHeaders();

        // 1. Trouver la structure par le token
        Optional<Structure> structureOpt = structureService.findByConfirmationToken(confirmationToken);

        if (structureOpt.isEmpty()) {
            headers.add("Location", "http://votre-frontend-domain.com/login");
            return new ResponseEntity<>("Token de confirmation invalide ou expiré.", HttpStatus.BAD_REQUEST);
            
        }

       /*  if (structureOpt.getTokenExpiryDate().isBefore(LocalDateTime.now())) {
            // Le token est expiré. Optionnel: on peut nettoyer le token expiré ici
          return new ResponseEntity<>("Token de confirmation invalide ou expiré.", HttpStatus.BAD_REQUEST);
        } */ 
        

        Structure structure = structureOpt.get();
        
        // 2. Mettre à jour isActive à "true"
        structure.setIsActive("true");
        structure.setConfirmationToken(null); // Optionnel: Invalider le token après usage
        structureService.createStructure(structure);
        
        // 3. Rediriger ou retourner un message de succès
        headers.add("Location", "http://localhost:5173/login");
        return new ResponseEntity<>("Votre structure a été activée avec succès!", HttpStatus.OK);
        
        // Vous pouvez aussi utiliser un RedirectView pour rediriger vers une page de succès
    }
}
    
