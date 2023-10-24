package com.example.blogapi.controller;

import com.example.blogapi.model.Category;
import com.example.blogapi.service.interfaces.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categories")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;

    @GetMapping("")
    public ResponseEntity<List<Category>> getAllCategories() {
        return ResponseEntity.ok(categoryService.getAllCategories());
    }

    @GetMapping("/{identifier}")
    public ResponseEntity<Category> getCategoryByIdOrSlug(@PathVariable("identifier") String identifier) {
        // Check if the identifier matches the pattern for an ID (numeric) or a slug (alphanumeric with hyphens)
        if (identifier.matches("\\d+")) {
            Long id = Long.valueOf(identifier);
            return ResponseEntity.ok(categoryService.getCategoryById(id));
        } else {
            return ResponseEntity.ok(categoryService.getCategoryBySlug(identifier));
        }
    }

    @PostMapping("")
    public ResponseEntity<Category> createCategory(@RequestBody Category category) {
        return ResponseEntity.ok(categoryService.createCategory(category));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Category> updateCategory(@PathVariable Long id, @RequestBody Category category) {
        return ResponseEntity.ok(categoryService.updateCategory(id, category));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Integer> deleteCategory(@PathVariable Long id) {
        return ResponseEntity.ok(categoryService.deleteCategoryById(id));
    }
}
