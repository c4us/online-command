package io.c4us.masterbackend.ressource;

import java.net.URI;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.c4us.masterbackend.DTOs.CategoryStatusUpdateDTO;
import io.c4us.masterbackend.domain.Category;
import io.c4us.masterbackend.service.CategoryService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/category")
@CrossOrigin(origins = "http://localhost:5173")
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

    @PatchMapping("/updateStatus/{id}")
    public ResponseEntity<Category> updateStatus(
            @PathVariable String id,
            @RequestBody CategoryStatusUpdateDTO dto) {

        Category updated = categoryService.updateActiveStatus(id, dto.isActive());
        return ResponseEntity.ok(updated);
    }

    @GetMapping
    public List<Category> getAllCategories() {
        return categoryService.findByCodeStructure();

    }

    @GetMapping("/{id}")
    public ResponseEntity<Category> getCategory(@PathVariable(value = "id") String id) {
        return ResponseEntity.ok().body(categoryService.getCategory(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Category> delCategorie(@PathVariable(value = "id") String id) {
        return ResponseEntity.ok().body(categoryService.delCategory(id));
    }

    @GetMapping("/structure/{userId}")
    public ResponseEntity<List<Category>> getByUser(@PathVariable String userId) {
        return ResponseEntity.ok(categoryService.getCategoryByStructure(userId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Category> updateCategory(
            @PathVariable String id,
            @RequestBody Category categoryDetails) {
        Category category = categoryService.getCategory(id);

        category.setNameCat(categoryDetails.getNameCat());
        category.setDescription(categoryDetails.getDescription());
        category.setCodeStructure(categoryDetails.getCodeStructure());

        Category updatedCategory = categoryService.updateCategory(category);
        return ResponseEntity.ok(updatedCategory);
    }


}
