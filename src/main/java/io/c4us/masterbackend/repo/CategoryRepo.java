package io.c4us.masterbackend.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import io.c4us.masterbackend.domain.Category;


public interface CategoryRepo extends JpaRepository<io.c4us.masterbackend.domain.Category, String> {
     Optional<io.c4us.masterbackend.domain.Category> findById(String id);

     List<Category> findByCodeStructure(String codestructure);

}
