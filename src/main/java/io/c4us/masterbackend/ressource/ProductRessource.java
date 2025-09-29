package io.c4us.masterbackend.ressource;

import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;


import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import static io.c4us.masterbackend.constant.Constant.PHOTO_DIRECTORY;

import io.c4us.masterbackend.domain.Product;
import io.c4us.masterbackend.service.ProductService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/product")
@RequiredArgsConstructor
public class ProductRessource {

    private final ProductService productService;

    @PostMapping
    public ResponseEntity<Product> createProduct(@RequestBody Product product) {
        try {
            return ResponseEntity.created(URI.create("/product/userID")).body(productService.createProduct(product));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/update")
    public ResponseEntity<Product> updateProduct(@RequestBody Product product) {
        return ResponseEntity.created(URI.create("/product/update/userID")).body(productService.updateProduct(product));
    }

    @GetMapping
    public ResponseEntity<Page<Product>> getProduct(@RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size) {
        return ResponseEntity.ok().body(productService.getAllProduct(page, size));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> geProduct(@PathVariable(value = "id") String id) {
        return ResponseEntity.ok().body(productService.getProduct(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Product> delProduct(@PathVariable(value = "id") String id) {
        return ResponseEntity.ok().body(productService.delProduct(id));
    }

    @PutMapping("/photo")
    public ResponseEntity<String> uploadPhoto(@RequestParam("id") String id, @RequestParam("file") MultipartFile file) {
        return ResponseEntity.ok().body(productService.uploadPhoto(id, file));
    }

    @GetMapping(path = "/image/{filename}", produces = { org.springframework.http.MediaType.IMAGE_PNG_VALUE,
            org.springframework.http.MediaType.IMAGE_JPEG_VALUE })
    public byte[] getPhoto(@PathVariable("filename") String filename) throws IOException {
        return Files.readAllBytes(Paths.get(PHOTO_DIRECTORY + filename));
    }

    @GetMapping("/category/{categoryId}")
    public List<Product> getProductsByCategoryId(@PathVariable String categoryId) {
        return productService.getAllProductByCat(categoryId);
    }
}
