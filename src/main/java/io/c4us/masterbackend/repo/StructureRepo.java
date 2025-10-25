package io.c4us.masterbackend.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import io.c4us.masterbackend.domain.Structure;

public interface StructureRepo extends JpaRepository<Structure, String> {
    List<Structure> findByCreatedUserId(String userId);

}
