package io.c4us.masterbackend.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import io.c4us.masterbackend.domain.AppUser;
import io.c4us.masterbackend.domain.Structure;

public interface AppUserRepo extends JpaRepository<AppUser, String> {
    Optional<AppUser> findByConfirmationToken(String confirmationToken);
    Optional<AppUser> findById(String id);
    AppUser findByUserEmail(String confirmationToken);

}
