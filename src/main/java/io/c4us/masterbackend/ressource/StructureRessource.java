package io.c4us.masterbackend.ressource;

import java.net.URI;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
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
    
}
