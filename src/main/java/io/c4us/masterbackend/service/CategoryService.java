package io.c4us.masterbackend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import io.c4us.masterbackend.domain.Category;
import io.c4us.masterbackend.repo.CategoryRepo;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

// TEST
@Service
@Slf4j
@Transactional(rollbackOn = Exception.class)
@RequiredArgsConstructor
public class CategoryService {

    @Autowired
    private CategoryRepo categoryRepo;

    public Category delCategory(String id) {
        try {
            Category category = getCategory(id);
            categoryRepo.deleteById(id);
            return category;
        } catch (Exception exception) {
            throw new RuntimeException();
        }
    }

    public Category getCategory(String id) {
        return categoryRepo.findById(id).orElseThrow(() -> new RuntimeException("Categoty Not found"));
    }

    public Page<Category> getAllCategories(int page, int size) {

        return categoryRepo.findAll(PageRequest.of(page, size, Sort.by("createdDate")));

    }

    public Category updateCategory(Category newcat) {
        try {
            Category category = getCategory(newcat.getId());
            category.setCategoryId(newcat.getCategoryId());
            categoryRepo.save(category);
            return category;
        } catch (Exception exception) {
            throw new RuntimeException();
        }
    }

    public Category createCategory(Category category) {
        return categoryRepo.save(category);
    }

}
