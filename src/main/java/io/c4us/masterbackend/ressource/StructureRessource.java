package io.c4us.masterbackend.ressource;

import static io.c4us.masterbackend.constant.Constant.PHOTO_DIRECTORY;

import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import io.c4us.masterbackend.domain.Structure;
import io.c4us.masterbackend.service.StructureService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/structure")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")
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

    @GetMapping(path = "/structures/image/{filename}", produces = { org.springframework.http.MediaType.IMAGE_PNG_VALUE,
            org.springframework.http.MediaType.IMAGE_JPEG_VALUE })
    public byte[] getPhoto(@PathVariable("filename") String filename) throws IOException {
        return Files.readAllBytes(Paths.get(PHOTO_DIRECTORY + filename));
    }
    // ... suite du StructureController

    @PutMapping("/photo")
    public ResponseEntity<String> uploadPhoto(@RequestParam("id") String id, @RequestParam("file") MultipartFile file) {
        return ResponseEntity.ok().body(structureService.uploadPhoto(id, file));
    }

}
