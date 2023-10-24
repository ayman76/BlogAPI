package com.example.blogapi.service.impls;

import com.example.blogapi.model.Category;
import com.example.blogapi.repository.CategoryRepo;
import com.example.blogapi.service.interfaces.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepo categoryRepo;

    @Override
    public Category getCategoryById(Long id) {
        Optional<Category> foundedCategory = categoryRepo.findById(id);
        if (foundedCategory.isPresent()) {
            return foundedCategory.get();
        }
        throw new RuntimeException("Not Founded Category with id " + id);
    }

    @Override
    public List<Category> getAllCategories() {
        return categoryRepo.findAll();
    }

    @Override
    public Category createCategory(Category category) {
        return categoryRepo.save(category);
    }

    @Override
    public Category updateCategory(Long id, Category category) {
        Optional<Category> foundedCategory = categoryRepo.findById(id);
        if (foundedCategory.isPresent()) {
            Category updatedCategory = foundedCategory.get();
            updatedCategory.setName(category.getName());
            updatedCategory.setSlug(category.getSlug());
            return categoryRepo.save(updatedCategory);
        }
        throw new RuntimeException("Not Founded Category with id " + id);
    }

    @Override
    public int deleteCategoryById(Long id) {
        Optional<Category> foundedCategory = categoryRepo.findById(id);
        if (foundedCategory.isPresent()) {
            categoryRepo.deleteById(id);
            return 1;
        }
        throw new RuntimeException("Not Founded Category with id " + id);
    }

    @Override
    public Category getCategoryBySlug(String slug) {
        Optional<Category> foundedCategory = categoryRepo.findBySlug(slug);
        if (foundedCategory.isPresent()) {
            return foundedCategory.get();
        }

        throw new RuntimeException("Not Founded Category with slug " + slug);
    }
}
