package io.c4us.masterbackend.service;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import static io.c4us.masterbackend.constant.Constant.PHOTO_DIRECTORY;
import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;
import org.springframework.stereotype.Service;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import io.c4us.masterbackend.domain.Product;
import io.c4us.masterbackend.repo.ProductRepo;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@Transactional(rollbackOn = Exception.class)
@RequiredArgsConstructor
public class ProductService {

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
            return ServletUriComponentsBuilder.fromCurrentContextPath().path("/cars/image/" + filename).toUriString();
        } catch (Exception exception) {
            throw new RuntimeException();
        }

    };
    @Autowired
    private ProductRepo productRepo;

    public Page<Product> getAllProduct(int page, int size) {

        return productRepo.findAll(PageRequest.of(page, size, Sort.by("createdDate")));

    }

    public Product getProduct(String id) {
        return productRepo.findById(id).orElseThrow(() -> new RuntimeException("Car not found"));
    }

    public Product delProduct(String id) {
        try {
            Product product = getProduct(id);
            productRepo.deleteById(id);
            return product;
        } catch (Exception exception) {
            throw new RuntimeException();
        }
    }

    public Product updateProduct(Product newcar) {
        try {
            Product product = getProduct(newcar.getId());
            product.setProductId(newcar.getProductId());
            productRepo.save(product);
            return product;
        } catch (Exception exception) {
            throw new RuntimeException();
        }
    }

    public Product createProduct(Product product) {
        return productRepo.save(product);
    }

    public String uploadPhoto(String id, MultipartFile file) {
        log.info("Upload photo for car : {}", id);
        Product product = getProduct(id);
        String photoUrl = photoFunction.apply(id, file);
        product.setProductPhotoUrl(photoUrl);
        productRepo.save(product);
        return photoUrl;

    }

    public List<Product> getAllProductByCat(String idCat) {
        return productRepo.findByCategoryId(idCat);

    }

}
