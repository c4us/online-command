package io.c4us.masterbackend.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepo extends JpaRepository<io.c4us.masterbackend.domain.Category, String> {
     Optional<io.c4us.masterbackend.domain.Category> findById(String id);
}
