package io.c4us.masterbackend.service;

import static io.c4us.masterbackend.constant.Constant.PHOTO_DIRECTORY;
import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

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
        Structure st = structureRepo.save(struct);
        return st;

    }

    private final Function<String, String> fileExtension = filename -> Optional.of(filename)
            .filter(name -> name.contains("."))
            .map(name -> "." + name.substring(filename.lastIndexOf(".") + 1)).orElse(".png");
    private final BiFunction<String, MultipartFile, String> photoFunction = (id, image) -> {
        String filename = id + fileExtension.apply(image.getOriginalFilename());
        try {
            Path fileStorageLocation = Paths.get(PHOTO_DIRECTORY).toAbsolutePath().normalize();
            if (!Files.exists(fileStorageLocation)) {
                Files.createDirectory(fileStorageLocation);
            }
            Files.copy(image.getInputStream(), fileStorageLocation.resolve(filename), REPLACE_EXISTING);
            return ServletUriComponentsBuilder.fromCurrentContextPath().path("/structure/image/" + filename).toUriString();
        } catch (Exception exception) {
            throw new RuntimeException();
        }

    };

    public String uploadPhoto(String id, MultipartFile file) {
        log.info("Upload photo for structure : {}", id);
        Structure struct = getStructure(id);
        String photoUrl = photoFunction.apply(id, file);
        struct.setStructPhotoUrl(photoUrl);
        structureRepo.save(struct);
        return photoUrl;

    }

    public Structure getStructure(String id) {
        return structureRepo.findById(id).orElseThrow(() -> new RuntimeException("Structure not found : Id"+id));
    }

    public List<Structure> getStructuresByUser(String userId) {
        return structureRepo.findByCreatedUserId(userId);
    }

    public List<Structure> getAllStructure(){
        return structureRepo.findAll();
    }


}
