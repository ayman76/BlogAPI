package com.example.categorymicroservice.service.impl;


import com.example.categorymicroservice.dto.CategoryDto;
import com.example.categorymicroservice.model.Category;
import com.example.categorymicroservice.repository.CategoryRepo;
import com.example.categorymicroservice.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final ModelMapper modelMapper;
    private final CategoryRepo categoryRepo;


    @Override
    public CategoryDto getCategoryById(Long id) {
        Category foundedCategory = categoryRepo.findById(id).orElseThrow(() -> new RuntimeException("Not Founded Category with id: " + id));
        return modelMapper.map(foundedCategory, CategoryDto.class);
    }

    @Override
    public List<CategoryDto> getAllCategories() {
        return categoryRepo.findAll().stream().map(c -> modelMapper.map(c, CategoryDto.class)).collect(Collectors.toList());
    }

    @Override
    public CategoryDto createCategory(CategoryDto categoryDto) {
        Category category = modelMapper.map(categoryDto, Category.class);
        Category newCategory = categoryRepo.save(category);
        return modelMapper.map(newCategory, CategoryDto.class);
    }

    @Override
    public CategoryDto updateCategory(Long id, CategoryDto categoryDto) {
        Category foundedCategory = categoryRepo.findById(id).orElseThrow(() -> new RuntimeException("Not Founded Category with id: " + id));
        foundedCategory.setName(categoryDto.getName());
        foundedCategory.setSlug(categoryDto.getSlug());
        Category updatedCategory = categoryRepo.save(foundedCategory);
        return modelMapper.map(updatedCategory, CategoryDto.class);
    }

    @Override
    public int deleteCategoryById(Long id) {
        Category foundedCategory = categoryRepo.findById(id).orElseThrow(() -> new RuntimeException("Not Founded Category with id: " + id));
        categoryRepo.deleteById(foundedCategory.getId());
        return id.intValue();
    }

    @Override
    public CategoryDto getCategoryBySlug(String slug) {
        Category foundedCategory = categoryRepo.findBySlug(slug).orElseThrow(() -> new RuntimeException("Not Founded Category with slug: " + slug));
        return modelMapper.map(foundedCategory, CategoryDto.class);

    }

}
