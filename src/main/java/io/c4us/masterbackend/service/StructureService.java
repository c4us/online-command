package io.c4us.masterbackend.service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.c4us.masterbackend.config.EmailService;
import io.c4us.masterbackend.domain.Structure;
import io.c4us.masterbackend.repo.StructureRepo;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@Transactional(rollbackOn = Exception.class)
@RequiredArgsConstructor
public class StructureService {

    @Autowired
    private StructureRepo structureRepo;

    public Structure createStructure(Structure struct) {
        struct.setConfirmationToken(generateAndSetToken(struct));
        Structure st = structureRepo.save(struct);
        return st;

    }

    public String generateAndSetToken(Structure structure) {
        // 1. Générer un Token robuste (UUID)
        String token = UUID.randomUUID().toString();

        // 2. Définir la durée d'expiration (24 heures)
        LocalDateTime expiryDate = LocalDateTime.now().plusHours(24);

        // 3. Mettre à jour la structure
        structure.setConfirmationToken(token);
        structure.setTokenExpiryDate(expiryDate);

        // Le service doit ensuite persister ces changements
        // (repository.save(structure))

        return token;
    }

    public Optional<Structure> findByConfirmationToken(String token) {
        // L'appel utilise l'objet injecté structureRepository
        return structureRepo.findByConfirmationToken(token);
    }

}
