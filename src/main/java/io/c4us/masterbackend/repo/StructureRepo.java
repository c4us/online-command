package io.c4us.masterbackend.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import io.c4us.masterbackend.domain.Structure;

public interface StructureRepo extends JpaRepository<Structure, String> {
    Optional<Structure> findByConfirmationToken(String confirmationToken);

}
