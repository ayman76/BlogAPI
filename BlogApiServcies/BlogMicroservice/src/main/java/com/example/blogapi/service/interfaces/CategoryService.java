package com.example.blogapi.service.interfaces;

import com.example.blogapi.dto.CategoryDto;

import java.util.List;

public interface CategoryService {
    CategoryDto getCategoryById(Long id);

    List<CategoryDto> getAllCategories();

    CategoryDto createCategory(CategoryDto categoryDto);

    CategoryDto updateCategory(Long id, CategoryDto categoryDto);

    int deleteCategoryById(Long id);

    CategoryDto getCategoryBySlug(String slug);
}
