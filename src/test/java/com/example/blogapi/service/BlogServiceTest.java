package com.example.blogapi.service;

import com.example.blogapi.dto.AppUserDto;
import com.example.blogapi.dto.CategoryDto;
import com.example.blogapi.dto.blog.BlogDto;
import com.example.blogapi.dto.blog.BlogRequestDto;
import com.example.blogapi.model.AppUser;
import com.example.blogapi.model.Blog;
import com.example.blogapi.model.Category;
import com.example.blogapi.model.Tag;
import com.example.blogapi.repository.AppUserRepo;
import com.example.blogapi.repository.BlogRepo;
import com.example.blogapi.repository.CategoryRepo;
import com.example.blogapi.repository.TagRepo;
import com.example.blogapi.service.impls.BlogServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BlogServiceTest {

    private final Long BLOG_ID = 1L;
    private Category category;
    @Mock
    private BlogRepo blogRepo;
    @Mock
    private AppUserRepo appUserRepo;
    @Mock
    private CategoryRepo categoryRepo;
    @Mock
    private TagRepo tagRepo;
    @InjectMocks
    private BlogServiceImpl blogService;
    private Blog blog;
    private AppUser appUser;
    private BlogRequestDto blogRequestDto;

    @BeforeEach
    public void setUp() {
        ModelMapper modelMapper = new ModelMapper();
        blogService = new BlogServiceImpl(modelMapper, blogRepo, categoryRepo, appUserRepo, tagRepo);
        appUser = AppUser.builder().name("Ayman").email("ayman@gmail.com").password("password12").username("ayman00").build();
        Long APP_USER_ID = 1L;
        AppUserDto appUserDto = AppUserDto.builder().id(APP_USER_ID).name("Ayman").email("ayman@gmail.com").password("password12").username("ayman00").build();
        category = Category.builder().name("category").slug("category-slug").build();
        CategoryDto categoryDto = CategoryDto.builder().id(APP_USER_ID).name("category").slug("category-slug").build();
        blog = Blog.builder().id(BLOG_ID).title("blog").description("blog description").slug("blog-slug").user(appUser).category(category).build();
        blogRequestDto = BlogRequestDto.builder().title("blog").description("blog description").slug("blog-slug").userId(appUserDto.getId()).categoryId(categoryDto.getId()).tags(Arrays.asList(1L, 2L)).build();
    }

    @Test
    public void BlogService_CreateBlog_ReturnBlogDto() {

        Tag tag = Tag.builder().id(1L).name("tag").build();
        when(categoryRepo.findById(Mockito.anyLong())).thenReturn(Optional.ofNullable(category));
        when(appUserRepo.findById(Mockito.anyLong())).thenReturn(Optional.ofNullable(appUser));
        when(tagRepo.findById(Mockito.anyLong())).thenReturn(Optional.ofNullable(tag));
        when(blogRepo.save(any(Blog.class))).thenReturn(blog);

        BlogDto savedBlog = blogService.createBlog(blogRequestDto);

        Assertions.assertThat(savedBlog).isNotNull();
    }

    @Test
    public void BlogService_GetBlogById_ReturnBlogDto() {
        when(blogRepo.findById(Mockito.anyLong())).thenReturn(Optional.ofNullable(blog));

        BlogDto foundedBlog = blogService.getBlogById(BLOG_ID);

        Assertions.assertThat(foundedBlog).isNotNull();
    }

    @Test
    public void BlogService_GetBlogBySlug_ReturnBlogDto() {
        when(blogRepo.findBlogBySlug(Mockito.anyString())).thenReturn(Optional.ofNullable(blog));

        String BLOG_SLUG = "blog-slug";
        BlogDto foundedBlog = blogService.getBlogBySlug(BLOG_SLUG);

        Assertions.assertThat(foundedBlog).isNotNull();
    }

    @Test
    public void BlogService_GetAllBlogs_ReturnBlogDtos() {
        List<Blog> blogs = new ArrayList<>(Arrays.asList(blog, blog));

        when(blogRepo.findAll()).thenReturn(blogs);

        List<BlogDto> blogDtos = blogService.getAllBlogs();

        Assertions.assertThat(blogDtos).isNotEmpty();
        Assertions.assertThat(blogDtos.size()).isEqualTo(blogs.size());
    }

    @Test
    public void BlogService_UpdateBlog_ReturnBlogDto() {
        Tag tag1 = Tag.builder().id(1L).name("tag").build();
        Tag tag2 = Tag.builder().id(2L).name("tag").build();
        blog.setTags(Set.of(tag1, tag2));
        when(blogRepo.findById(Mockito.anyLong())).thenReturn(Optional.ofNullable(blog));
        when(categoryRepo.findById(Mockito.anyLong())).thenReturn(Optional.ofNullable(category));
        when(tagRepo.findById(1L)).thenReturn(Optional.of(tag1));
        when(tagRepo.findById(2L)).thenReturn(Optional.of(tag2));
        when(blogRepo.save(any(Blog.class))).thenReturn(blog);

        BlogDto updatedBlog = blogService.updateBlog(BLOG_ID, blogRequestDto);

        Assertions.assertThat(updatedBlog).isNotNull();
    }

    @Test
    public void BlogService_DeleteBlog_ReturnBlogId() {
        when(blogRepo.findById(Mockito.anyLong())).thenReturn(Optional.ofNullable(blog));

        int deletedBlogId = blogService.deleteBlogById(BLOG_ID);

        Assertions.assertThat(deletedBlogId).isEqualTo(BLOG_ID.intValue());
    }
}