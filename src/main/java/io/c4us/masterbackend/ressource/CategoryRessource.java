package io.c4us.masterbackend.ressource;

import java.net.URI;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.c4us.masterbackend.domain.Category;
import io.c4us.masterbackend.service.CategoryService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/category")
@RequiredArgsConstructor
public class CategoryRessource {
    private final CategoryService categoryService;

    @PostMapping
    public ResponseEntity<Category> createCategory(@RequestBody Category category) {
        try {
            return ResponseEntity.created(URI.create("/category/userID"))
                    .body(categoryService.createCategory(category));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/update")
    public ResponseEntity<Category> updateCar(@RequestBody Category category) {
        return ResponseEntity.created(URI.create("/category/update/userID"))
                .body(categoryService.updateCategory(category));
    }

    @GetMapping
    public ResponseEntity<Page<Category>> getCategory(@RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size) {
        return ResponseEntity.ok().body(categoryService.getAllCategories(page, size));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Category> getCategory(@PathVariable(value = "id") String id) {
        return ResponseEntity.ok().body(categoryService.getCategory(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Category> delCategorie(@PathVariable(value = "id") String id) {
        return ResponseEntity.ok().body(categoryService.delCategory(id));
    }

}
