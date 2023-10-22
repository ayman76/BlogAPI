package com.example.blogapi.service.interfaces;

import com.example.blogapi.model.Category;

import java.util.List;

public interface CategoryService {
    Category getCategoryById(Long id);

    List<Category> getAllCategories();

    Category createCategory(Category category);

    Category updateCategory(Long id, Category category);

    int deleteCategoryById(Long id);
}
