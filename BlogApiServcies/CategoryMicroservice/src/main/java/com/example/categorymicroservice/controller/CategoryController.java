package com.example.categorymicroservice.controller;

import com.example.categorymicroservice.dto.CategoryDto;
import com.example.categorymicroservice.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/categories")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;

    @GetMapping("")
    public ResponseEntity<List<CategoryDto>> getAllCategories() {
        return ResponseEntity.ok(categoryService.getAllCategories());
    }

    @GetMapping("/{identifier}")
    public ResponseEntity<CategoryDto> getCategoryByIdOrSlug(@PathVariable("identifier") String identifier) {
        // Check if the identifier matches the pattern for an ID (numeric) or a slug (alphanumeric with hyphens)
        if (identifier.matches("\\d+")) {
            Long id = Long.valueOf(identifier);
            return ResponseEntity.ok(categoryService.getCategoryById(id));
        } else {
            return ResponseEntity.ok(categoryService.getCategoryBySlug(identifier));
        }
    }

    @PostMapping("")
    public ResponseEntity<CategoryDto> createCategory(@RequestBody CategoryDto category) {
        return ResponseEntity.ok(categoryService.createCategory(category));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoryDto> updateCategory(@PathVariable Long id, @RequestBody CategoryDto category) {
        return ResponseEntity.ok(categoryService.updateCategory(id, category));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Integer> deleteCategory(@PathVariable Long id) {
        return ResponseEntity.ok(categoryService.deleteCategoryById(id));
    }
}
