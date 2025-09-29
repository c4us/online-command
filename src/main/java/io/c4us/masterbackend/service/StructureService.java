package io.c4us.masterbackend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        return structureRepo.save(struct);
    }
}
