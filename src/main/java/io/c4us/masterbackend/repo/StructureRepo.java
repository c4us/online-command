package io.c4us.masterbackend.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import io.c4us.masterbackend.domain.Structure;

public interface StructureRepo extends JpaRepository<Structure,String>{
    
}
