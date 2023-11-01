package com.example.blogapi.controller;

import com.example.blogapi.dto.CategoryDto;
import com.example.blogapi.service.interfaces.CategoryService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@WebMvcTest(controllers = CategoryController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
class CategoryControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private CategoryService categoryService;

    @Autowired
    private ObjectMapper objectMapper;

    private CategoryDto categoryDto;
    private List<CategoryDto> categoryDtoList;

    @BeforeEach
    public void setUp() {
        categoryDto = CategoryDto.builder().id(1L).name("category").slug("categorySlug").build();
        categoryDtoList = new ArrayList<>();
        categoryDtoList.addAll(Arrays.asList(categoryDto, categoryDto));
    }

    @Test
    public void CategoryController_CreateCategory_ReturnsIsOk() throws Exception {
        when(categoryService.createCategory(Mockito.any(CategoryDto.class))).thenReturn(categoryDto);

        ResultActions response = mockMvc.perform(post("/categories").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(categoryDto)));

        response.andExpect(MockMvcResultMatchers.status().isOk()).andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void CategoryController_GetCategoryById_ReturnsIsOk() throws Exception {
        when(categoryService.getCategoryById(Mockito.anyLong())).thenReturn(categoryDto);

        ResultActions response = mockMvc.perform(get("/categories/1").contentType(MediaType.APPLICATION_JSON));

        response.andExpect(MockMvcResultMatchers.status().isOk()).andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void CategoryController_GetCategoryBySlug_ReturnsIsOk() throws Exception {
        when(categoryService.getCategoryBySlug(Mockito.anyString())).thenReturn(categoryDto);

        ResultActions response = mockMvc.perform(get("/categories/category-slug").contentType(MediaType.APPLICATION_JSON));

        response.andExpect(MockMvcResultMatchers.status().isOk()).andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void CategoryController_GetAllCategories_ReturnsIsOk() throws Exception {
        when(categoryService.getAllCategories()).thenReturn(categoryDtoList);

        ResultActions response = mockMvc.perform(get("/categories").contentType(MediaType.APPLICATION_JSON));

        response.andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.jsonPath("$.size()", CoreMatchers.is(categoryDtoList.size()))).andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void CategoryController_UpdateCategory_ReturnsIsOk() throws Exception {
        when(categoryService.updateCategory(Mockito.anyLong(), Mockito.any(CategoryDto.class))).thenReturn(categoryDto);

        ResultActions response = mockMvc.perform(put("/categories/1").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(categoryDto)));

        response.andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.jsonPath("$.name", CoreMatchers.is(categoryDto.getName()))).andExpect(MockMvcResultMatchers.jsonPath("$.slug", CoreMatchers.is(categoryDto.getSlug()))).andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void CategoryController_DeleteCategory_ReturnsIsOk() throws Exception {
        when(categoryService.deleteCategoryById(Mockito.anyLong())).thenReturn(categoryDto.getId().intValue());

        ResultActions response = mockMvc.perform(delete("/categories/1").contentType(MediaType.APPLICATION_JSON));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", CoreMatchers.is(categoryDto.getId().intValue())))
                .andDo(MockMvcResultHandlers.print());
    }


}