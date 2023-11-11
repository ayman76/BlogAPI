package com.example.blogapi.service.interfaces;

import com.example.blogapi.dto.blog.BlogDto;
import com.example.blogapi.dto.blog.BlogRequestDto;

import java.util.List;

public interface BlogService {

    BlogDto getBlogById(Long id);

    List<BlogDto> getAllBlogs();

    BlogDto createBlog(BlogRequestDto blogDto);

    BlogDto updateBlog(Long id, BlogRequestDto blogDto) throws RuntimeException;

    int deleteBlogById(Long id);

    BlogDto getBlogBySlug(String slug);


}
