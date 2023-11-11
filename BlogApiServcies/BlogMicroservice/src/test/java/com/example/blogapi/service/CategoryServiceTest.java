package com.example.blogapi.service;

import com.example.blogapi.dto.CategoryDto;
import com.example.blogapi.model.Category;
import com.example.blogapi.repository.CategoryRepo;
import com.example.blogapi.service.impls.CategoryServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CategoryServiceTest {

    Long CATEGORY_ID = 1L;
    String CATEGORY_SLUG = "category-slug";
    @Mock
    private CategoryRepo categoryRepo;

    @InjectMocks
    private CategoryServiceImpl categoryService;
    private Category category;
    private CategoryDto categoryDto;
    private List<Category> categoryList;

    @BeforeEach
    public void setUp() {
        ModelMapper modelMapper = new ModelMapper();
        categoryService = new CategoryServiceImpl(modelMapper, categoryRepo);
        category = Category.builder().name("category").slug("categorySlug").build();
        categoryDto = CategoryDto.builder().name("category").slug("categorySlug").build();
        categoryList = new ArrayList<>();
        categoryList.addAll(Arrays.asList(category, category));
    }

    @Test
    public void CategoryService_CreateCategory_ReturnCategoryDto() {
        when(categoryRepo.save(Mockito.any(Category.class))).thenReturn(category);

        CategoryDto savedCategory = categoryService.createCategory(categoryDto);
        Assertions.assertThat(savedCategory).isNotNull();
    }

    @Test
    public void CategoryService_GetCategoryById_ReturnCategoryDto() {
        when(categoryRepo.findById(Mockito.anyLong())).thenReturn(Optional.ofNullable(category));

        CategoryDto foundedCategory = categoryService.getCategoryById(CATEGORY_ID);
        Assertions.assertThat(foundedCategory).isNotNull();
    }

    @Test
    public void CategoryService_GetCategoryBySlug_ReturnCategoryDto() {
        when(categoryRepo.findBySlug(Mockito.anyString())).thenReturn(Optional.ofNullable(category));

        CategoryDto foundedCategory = categoryService.getCategoryBySlug(CATEGORY_SLUG);
        Assertions.assertThat(foundedCategory).isNotNull();
    }

    @Test
    public void CategoryService_GetAllCategory_ReturnCategoryDtos() {
        when(categoryRepo.findAll()).thenReturn(categoryList);

        List<CategoryDto> categories = categoryService.getAllCategories();
        Assertions.assertThat(categories).isNotEmpty();
        Assertions.assertThat(categories.size()).isEqualTo(categoryList.size());
    }

    @Test
    public void CategoryService_UpdateCategory_ReturnCategoryDto() {
        when(categoryRepo.findById(Mockito.anyLong())).thenReturn(Optional.ofNullable(category));
        when(categoryRepo.save(Mockito.any(Category.class))).thenReturn(category);

        CategoryDto updatedCategory = categoryService.updateCategory(CATEGORY_ID, categoryDto);
        Assertions.assertThat(updatedCategory).isNotNull();
        Assertions.assertThat(updatedCategory.getName()).isEqualTo(categoryDto.getName());
        Assertions.assertThat(updatedCategory.getSlug()).isEqualTo(categoryDto.getSlug());
    }

    @Test
    public void CategoryService_DeleteCategory_ReturnCategoryId() {
        when(categoryRepo.findById(Mockito.anyLong())).thenReturn(Optional.ofNullable(category));

        int deleteCategoryId = categoryService.deleteCategoryById(CATEGORY_ID);
        Assertions.assertThat(deleteCategoryId).isEqualTo(CATEGORY_ID.intValue());
    }


}