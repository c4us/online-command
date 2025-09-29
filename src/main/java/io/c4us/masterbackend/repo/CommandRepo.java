package io.c4us.masterbackend.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import io.c4us.masterbackend.domain.Command;

public interface CommandRepo extends JpaRepository<Command, Long> {
    
}
