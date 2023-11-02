package com.example.blogapi.controller;

import com.example.blogapi.dto.AppUserDto;
import com.example.blogapi.dto.CategoryDto;
import com.example.blogapi.dto.TagDto;
import com.example.blogapi.dto.blog.BlogDto;
import com.example.blogapi.dto.blog.BlogRequestDto;
import com.example.blogapi.service.interfaces.BlogService;
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

@WebMvcTest(controllers = BlogController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
class BlogControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BlogService blogService;

    @Autowired
    private ObjectMapper objectMapper;

    private BlogRequestDto blogRequestDto;
    private BlogDto blogDto;

    @BeforeEach
    public void setUp() {
        AppUserDto appUserDto = AppUserDto.builder().id(1L).name("ayman").username("ayman00").email("ayman@gmail.com").password("ayman123").build();
        CategoryDto categoryDto = CategoryDto.builder().id(1L).name("category").slug("category-slug").build();
        TagDto tagDto = TagDto.builder().id(1L).name("tag").build();
        blogRequestDto = BlogRequestDto.builder().id(1L).title("blog")
                .description("blog description").slug("blog-slug")
                .categoryId(1L).userId(1L).tags(Arrays.asList(1L, 2L)).build();

        blogDto = BlogDto.builder().id(1L).title("blog")
                .description("blog description").slug("blog-slug")
                .category(categoryDto).appUser(appUserDto).tags(Arrays.asList(tagDto)).build();
    }

    @Test
    public void BlogController_CreateBlog_ReturnsIsOk() throws Exception {
        when(blogService.createBlog(blogRequestDto)).thenReturn(blogDto);

        ResultActions response = mockMvc.perform(post("/blogs")
                .content(objectMapper.writeValueAsString(blogRequestDto))
                .contentType(MediaType.APPLICATION_JSON));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void BlogController_GetAllBlogs_ReturnsIsOk() throws Exception {

        List<BlogDto> blogDtos = new ArrayList<>(Arrays.asList(blogDto, blogDto));
        when(blogService.getAllBlogs()).thenReturn(blogDtos);

        ResultActions response = mockMvc.perform(get("/blogs")
                .contentType(MediaType.APPLICATION_JSON));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()", CoreMatchers.is(blogDtos.size())))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void BlogController_GetBlogById_ReturnsIsOk() throws Exception {

        when(blogService.getBlogById(Mockito.anyLong())).thenReturn(blogDto);

        ResultActions response = mockMvc.perform(get("/blogs/1")
                .contentType(MediaType.APPLICATION_JSON));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void BlogController_GetBlogBySlug_ReturnsIsOk() throws Exception {

        when(blogService.getBlogBySlug(Mockito.anyString())).thenReturn(blogDto);

        ResultActions response = mockMvc.perform(get("/blogs/blog-slug")
                .contentType(MediaType.APPLICATION_JSON));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void BlogController_DeleteBlog_ReturnsIsOk() throws Exception {

        when(blogService.deleteBlogById(Mockito.anyLong())).thenReturn(blogDto.getId().intValue());

        ResultActions response = mockMvc.perform(delete("/blogs/1")
                .contentType(MediaType.APPLICATION_JSON));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", CoreMatchers.is(blogDto.getId().intValue())))
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void BlogController_UpdateBlog_ReturnsIsOk() throws Exception {

        when(blogService.updateBlog(Mockito.anyLong(), Mockito.any(BlogRequestDto.class))).thenReturn(blogDto);

        ResultActions response = mockMvc.perform(put("/blogs/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(blogRequestDto)));

        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }
}